import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeatureBilling, getFeatureBillingIdentifier } from '../feature-billing.model';

export type EntityResponseType = HttpResponse<IFeatureBilling>;
export type EntityArrayResponseType = HttpResponse<IFeatureBilling[]>;

@Injectable({ providedIn: 'root' })
export class FeatureBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/features');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(feature: IFeatureBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feature);
    return this.http
      .post<IFeatureBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(feature: IFeatureBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feature);
    return this.http
      .put<IFeatureBilling>(`${this.resourceUrl}/${getFeatureBillingIdentifier(feature) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(feature: IFeatureBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(feature);
    return this.http
      .patch<IFeatureBilling>(`${this.resourceUrl}/${getFeatureBillingIdentifier(feature) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IFeatureBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFeatureBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFeatureBillingToCollectionIfMissing(
    featureCollection: IFeatureBilling[],
    ...featuresToCheck: (IFeatureBilling | null | undefined)[]
  ): IFeatureBilling[] {
    const features: IFeatureBilling[] = featuresToCheck.filter(isPresent);
    if (features.length > 0) {
      const featureCollectionIdentifiers = featureCollection.map(featureItem => getFeatureBillingIdentifier(featureItem)!);
      const featuresToAdd = features.filter(featureItem => {
        const featureIdentifier = getFeatureBillingIdentifier(featureItem);
        if (featureIdentifier == null || featureCollectionIdentifiers.includes(featureIdentifier)) {
          return false;
        }
        featureCollectionIdentifiers.push(featureIdentifier);
        return true;
      });
      return [...featuresToAdd, ...featureCollection];
    }
    return featureCollection;
  }

  protected convertDateFromClient(feature: IFeatureBilling): IFeatureBilling {
    return Object.assign({}, feature, {
      lastUpdate: feature.lastUpdate?.isValid() ? feature.lastUpdate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((feature: IFeatureBilling) => {
        feature.lastUpdate = feature.lastUpdate ? dayjs(feature.lastUpdate) : undefined;
      });
    }
    return res;
  }
}
