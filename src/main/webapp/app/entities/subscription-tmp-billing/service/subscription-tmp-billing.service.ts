import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubscriptionTmpBilling, getSubscriptionTmpBillingIdentifier } from '../subscription-tmp-billing.model';

export type EntityResponseType = HttpResponse<ISubscriptionTmpBilling>;
export type EntityArrayResponseType = HttpResponse<ISubscriptionTmpBilling[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionTmpBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subscription-tmps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subscriptionTmp: ISubscriptionTmpBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionTmp);
    return this.http
      .post<ISubscriptionTmpBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subscriptionTmp: ISubscriptionTmpBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionTmp);
    return this.http
      .put<ISubscriptionTmpBilling>(`${this.resourceUrl}/${getSubscriptionTmpBillingIdentifier(subscriptionTmp) as string}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(subscriptionTmp: ISubscriptionTmpBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionTmp);
    return this.http
      .patch<ISubscriptionTmpBilling>(`${this.resourceUrl}/${getSubscriptionTmpBillingIdentifier(subscriptionTmp) as string}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ISubscriptionTmpBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubscriptionTmpBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubscriptionTmpBillingToCollectionIfMissing(
    subscriptionTmpCollection: ISubscriptionTmpBilling[],
    ...subscriptionTmpsToCheck: (ISubscriptionTmpBilling | null | undefined)[]
  ): ISubscriptionTmpBilling[] {
    const subscriptionTmps: ISubscriptionTmpBilling[] = subscriptionTmpsToCheck.filter(isPresent);
    if (subscriptionTmps.length > 0) {
      const subscriptionTmpCollectionIdentifiers = subscriptionTmpCollection.map(
        subscriptionTmpItem => getSubscriptionTmpBillingIdentifier(subscriptionTmpItem)!
      );
      const subscriptionTmpsToAdd = subscriptionTmps.filter(subscriptionTmpItem => {
        const subscriptionTmpIdentifier = getSubscriptionTmpBillingIdentifier(subscriptionTmpItem);
        if (subscriptionTmpIdentifier == null || subscriptionTmpCollectionIdentifiers.includes(subscriptionTmpIdentifier)) {
          return false;
        }
        subscriptionTmpCollectionIdentifiers.push(subscriptionTmpIdentifier);
        return true;
      });
      return [...subscriptionTmpsToAdd, ...subscriptionTmpCollection];
    }
    return subscriptionTmpCollection;
  }

  protected convertDateFromClient(subscriptionTmp: ISubscriptionTmpBilling): ISubscriptionTmpBilling {
    return Object.assign({}, subscriptionTmp, {
      lastUpdate: subscriptionTmp.lastUpdate?.isValid() ? subscriptionTmp.lastUpdate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((subscriptionTmp: ISubscriptionTmpBilling) => {
        subscriptionTmp.lastUpdate = subscriptionTmp.lastUpdate ? dayjs(subscriptionTmp.lastUpdate) : undefined;
      });
    }
    return res;
  }
}
