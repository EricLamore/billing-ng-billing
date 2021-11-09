import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductBilling, getProductBillingIdentifier } from '../product-billing.model';

export type EntityResponseType = HttpResponse<IProductBilling>;
export type EntityArrayResponseType = HttpResponse<IProductBilling[]>;

@Injectable({ providedIn: 'root' })
export class ProductBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/products');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(product: IProductBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(product);
    return this.http
      .post<IProductBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(product: IProductBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(product);
    return this.http
      .put<IProductBilling>(`${this.resourceUrl}/${getProductBillingIdentifier(product) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(product: IProductBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(product);
    return this.http
      .patch<IProductBilling>(`${this.resourceUrl}/${getProductBillingIdentifier(product) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IProductBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductBillingToCollectionIfMissing(
    productCollection: IProductBilling[],
    ...productsToCheck: (IProductBilling | null | undefined)[]
  ): IProductBilling[] {
    const products: IProductBilling[] = productsToCheck.filter(isPresent);
    if (products.length > 0) {
      const productCollectionIdentifiers = productCollection.map(productItem => getProductBillingIdentifier(productItem)!);
      const productsToAdd = products.filter(productItem => {
        const productIdentifier = getProductBillingIdentifier(productItem);
        if (productIdentifier == null || productCollectionIdentifiers.includes(productIdentifier)) {
          return false;
        }
        productCollectionIdentifiers.push(productIdentifier);
        return true;
      });
      return [...productsToAdd, ...productCollection];
    }
    return productCollection;
  }

  protected convertDateFromClient(product: IProductBilling): IProductBilling {
    return Object.assign({}, product, {
      lastUpdate: product.lastUpdate?.isValid() ? product.lastUpdate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((product: IProductBilling) => {
        product.lastUpdate = product.lastUpdate ? dayjs(product.lastUpdate) : undefined;
      });
    }
    return res;
  }
}
