import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRatePlanBilling, getRatePlanBillingIdentifier } from '../rate-plan-billing.model';

export type EntityResponseType = HttpResponse<IRatePlanBilling>;
export type EntityArrayResponseType = HttpResponse<IRatePlanBilling[]>;

@Injectable({ providedIn: 'root' })
export class RatePlanBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rate-plans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ratePlan: IRatePlanBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ratePlan);
    return this.http
      .post<IRatePlanBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ratePlan: IRatePlanBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ratePlan);
    return this.http
      .put<IRatePlanBilling>(`${this.resourceUrl}/${getRatePlanBillingIdentifier(ratePlan) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ratePlan: IRatePlanBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ratePlan);
    return this.http
      .patch<IRatePlanBilling>(`${this.resourceUrl}/${getRatePlanBillingIdentifier(ratePlan) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IRatePlanBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRatePlanBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRatePlanBillingToCollectionIfMissing(
    ratePlanCollection: IRatePlanBilling[],
    ...ratePlansToCheck: (IRatePlanBilling | null | undefined)[]
  ): IRatePlanBilling[] {
    const ratePlans: IRatePlanBilling[] = ratePlansToCheck.filter(isPresent);
    if (ratePlans.length > 0) {
      const ratePlanCollectionIdentifiers = ratePlanCollection.map(ratePlanItem => getRatePlanBillingIdentifier(ratePlanItem)!);
      const ratePlansToAdd = ratePlans.filter(ratePlanItem => {
        const ratePlanIdentifier = getRatePlanBillingIdentifier(ratePlanItem);
        if (ratePlanIdentifier == null || ratePlanCollectionIdentifiers.includes(ratePlanIdentifier)) {
          return false;
        }
        ratePlanCollectionIdentifiers.push(ratePlanIdentifier);
        return true;
      });
      return [...ratePlansToAdd, ...ratePlanCollection];
    }
    return ratePlanCollection;
  }

  protected convertDateFromClient(ratePlan: IRatePlanBilling): IRatePlanBilling {
    return Object.assign({}, ratePlan, {
      lastUpdate: ratePlan.lastUpdate?.isValid() ? ratePlan.lastUpdate.format(DATE_FORMAT) : undefined,
      activationDate: ratePlan.activationDate?.isValid() ? ratePlan.activationDate.format(DATE_FORMAT) : undefined,
      endDate: ratePlan.endDate?.isValid() ? ratePlan.endDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastUpdate = res.body.lastUpdate ? dayjs(res.body.lastUpdate) : undefined;
      res.body.activationDate = res.body.activationDate ? dayjs(res.body.activationDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ratePlan: IRatePlanBilling) => {
        ratePlan.lastUpdate = ratePlan.lastUpdate ? dayjs(ratePlan.lastUpdate) : undefined;
        ratePlan.activationDate = ratePlan.activationDate ? dayjs(ratePlan.activationDate) : undefined;
        ratePlan.endDate = ratePlan.endDate ? dayjs(ratePlan.endDate) : undefined;
      });
    }
    return res;
  }
}
