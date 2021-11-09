import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubscriptionFeatureBilling, getSubscriptionFeatureBillingIdentifier } from '../subscription-feature-billing.model';

export type EntityResponseType = HttpResponse<ISubscriptionFeatureBilling>;
export type EntityArrayResponseType = HttpResponse<ISubscriptionFeatureBilling[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionFeatureBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subscription-features');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(subscriptionFeature: ISubscriptionFeatureBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionFeature);
    return this.http
      .post<ISubscriptionFeatureBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subscriptionFeature: ISubscriptionFeatureBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionFeature);
    return this.http
      .put<ISubscriptionFeatureBilling>(
        `${this.resourceUrl}/${getSubscriptionFeatureBillingIdentifier(subscriptionFeature) as string}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(subscriptionFeature: ISubscriptionFeatureBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionFeature);
    return this.http
      .patch<ISubscriptionFeatureBilling>(
        `${this.resourceUrl}/${getSubscriptionFeatureBillingIdentifier(subscriptionFeature) as string}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ISubscriptionFeatureBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubscriptionFeatureBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubscriptionFeatureBillingToCollectionIfMissing(
    subscriptionFeatureCollection: ISubscriptionFeatureBilling[],
    ...subscriptionFeaturesToCheck: (ISubscriptionFeatureBilling | null | undefined)[]
  ): ISubscriptionFeatureBilling[] {
    const subscriptionFeatures: ISubscriptionFeatureBilling[] = subscriptionFeaturesToCheck.filter(isPresent);
    if (subscriptionFeatures.length > 0) {
      const subscriptionFeatureCollectionIdentifiers = subscriptionFeatureCollection.map(
        subscriptionFeatureItem => getSubscriptionFeatureBillingIdentifier(subscriptionFeatureItem)!
      );
      const subscriptionFeaturesToAdd = subscriptionFeatures.filter(subscriptionFeatureItem => {
        const subscriptionFeatureIdentifier = getSubscriptionFeatureBillingIdentifier(subscriptionFeatureItem);
        if (subscriptionFeatureIdentifier == null || subscriptionFeatureCollectionIdentifiers.includes(subscriptionFeatureIdentifier)) {
          return false;
        }
        subscriptionFeatureCollectionIdentifiers.push(subscriptionFeatureIdentifier);
        return true;
      });
      return [...subscriptionFeaturesToAdd, ...subscriptionFeatureCollection];
    }
    return subscriptionFeatureCollection;
  }

  protected convertDateFromClient(subscriptionFeature: ISubscriptionFeatureBilling): ISubscriptionFeatureBilling {
    return Object.assign({}, subscriptionFeature, {
      lastUpdate: subscriptionFeature.lastUpdate?.isValid() ? subscriptionFeature.lastUpdate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((subscriptionFeature: ISubscriptionFeatureBilling) => {
        subscriptionFeature.lastUpdate = subscriptionFeature.lastUpdate ? dayjs(subscriptionFeature.lastUpdate) : undefined;
      });
    }
    return res;
  }
}
