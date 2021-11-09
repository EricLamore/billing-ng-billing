import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganizationBilling, getOrganizationBillingIdentifier } from '../organization-billing.model';

export type EntityResponseType = HttpResponse<IOrganizationBilling>;
export type EntityArrayResponseType = HttpResponse<IOrganizationBilling[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organization: IOrganizationBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organization);
    return this.http
      .post<IOrganizationBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(organization: IOrganizationBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organization);
    return this.http
      .put<IOrganizationBilling>(`${this.resourceUrl}/${getOrganizationBillingIdentifier(organization) as string}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(organization: IOrganizationBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organization);
    return this.http
      .patch<IOrganizationBilling>(`${this.resourceUrl}/${getOrganizationBillingIdentifier(organization) as string}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IOrganizationBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrganizationBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrganizationBillingToCollectionIfMissing(
    organizationCollection: IOrganizationBilling[],
    ...organizationsToCheck: (IOrganizationBilling | null | undefined)[]
  ): IOrganizationBilling[] {
    const organizations: IOrganizationBilling[] = organizationsToCheck.filter(isPresent);
    if (organizations.length > 0) {
      const organizationCollectionIdentifiers = organizationCollection.map(
        organizationItem => getOrganizationBillingIdentifier(organizationItem)!
      );
      const organizationsToAdd = organizations.filter(organizationItem => {
        const organizationIdentifier = getOrganizationBillingIdentifier(organizationItem);
        if (organizationIdentifier == null || organizationCollectionIdentifiers.includes(organizationIdentifier)) {
          return false;
        }
        organizationCollectionIdentifiers.push(organizationIdentifier);
        return true;
      });
      return [...organizationsToAdd, ...organizationCollection];
    }
    return organizationCollection;
  }

  protected convertDateFromClient(organization: IOrganizationBilling): IOrganizationBilling {
    return Object.assign({}, organization, {
      registerDate: organization.registerDate?.isValid() ? organization.registerDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registerDate = res.body.registerDate ? dayjs(res.body.registerDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((organization: IOrganizationBilling) => {
        organization.registerDate = organization.registerDate ? dayjs(organization.registerDate) : undefined;
      });
    }
    return res;
  }
}
