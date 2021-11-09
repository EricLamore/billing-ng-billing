import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomerBilling, getCustomerBillingIdentifier } from '../customer-billing.model';

export type EntityResponseType = HttpResponse<ICustomerBilling>;
export type EntityArrayResponseType = HttpResponse<ICustomerBilling[]>;

@Injectable({ providedIn: 'root' })
export class CustomerBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customer: ICustomerBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .post<ICustomerBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customer: ICustomerBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .put<ICustomerBilling>(`${this.resourceUrl}/${getCustomerBillingIdentifier(customer) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(customer: ICustomerBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customer);
    return this.http
      .patch<ICustomerBilling>(`${this.resourceUrl}/${getCustomerBillingIdentifier(customer) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCustomerBillingToCollectionIfMissing(
    customerCollection: ICustomerBilling[],
    ...customersToCheck: (ICustomerBilling | null | undefined)[]
  ): ICustomerBilling[] {
    const customers: ICustomerBilling[] = customersToCheck.filter(isPresent);
    if (customers.length > 0) {
      const customerCollectionIdentifiers = customerCollection.map(customerItem => getCustomerBillingIdentifier(customerItem)!);
      const customersToAdd = customers.filter(customerItem => {
        const customerIdentifier = getCustomerBillingIdentifier(customerItem);
        if (customerIdentifier == null || customerCollectionIdentifiers.includes(customerIdentifier)) {
          return false;
        }
        customerCollectionIdentifiers.push(customerIdentifier);
        return true;
      });
      return [...customersToAdd, ...customerCollection];
    }
    return customerCollection;
  }

  protected convertDateFromClient(customer: ICustomerBilling): ICustomerBilling {
    return Object.assign({}, customer, {
      lastUpdate: customer.lastUpdate?.isValid() ? customer.lastUpdate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastUpdate = res.body.lastUpdate ? dayjs(res.body.lastUpdate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((customer: ICustomerBilling) => {
        customer.lastUpdate = customer.lastUpdate ? dayjs(customer.lastUpdate) : undefined;
      });
    }
    return res;
  }
}
