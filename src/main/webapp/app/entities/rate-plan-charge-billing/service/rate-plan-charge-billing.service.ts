import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRatePlanChargeBilling, getRatePlanChargeBillingIdentifier } from '../rate-plan-charge-billing.model';

export type EntityResponseType = HttpResponse<IRatePlanChargeBilling>;
export type EntityArrayResponseType = HttpResponse<IRatePlanChargeBilling[]>;

@Injectable({ providedIn: 'root' })
export class RatePlanChargeBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rate-plan-charges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ratePlanCharge: IRatePlanChargeBilling): Observable<EntityResponseType> {
    return this.http.post<IRatePlanChargeBilling>(this.resourceUrl, ratePlanCharge, { observe: 'response' });
  }

  update(ratePlanCharge: IRatePlanChargeBilling): Observable<EntityResponseType> {
    return this.http.put<IRatePlanChargeBilling>(
      `${this.resourceUrl}/${getRatePlanChargeBillingIdentifier(ratePlanCharge) as string}`,
      ratePlanCharge,
      { observe: 'response' }
    );
  }

  partialUpdate(ratePlanCharge: IRatePlanChargeBilling): Observable<EntityResponseType> {
    return this.http.patch<IRatePlanChargeBilling>(
      `${this.resourceUrl}/${getRatePlanChargeBillingIdentifier(ratePlanCharge) as string}`,
      ratePlanCharge,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IRatePlanChargeBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRatePlanChargeBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRatePlanChargeBillingToCollectionIfMissing(
    ratePlanChargeCollection: IRatePlanChargeBilling[],
    ...ratePlanChargesToCheck: (IRatePlanChargeBilling | null | undefined)[]
  ): IRatePlanChargeBilling[] {
    const ratePlanCharges: IRatePlanChargeBilling[] = ratePlanChargesToCheck.filter(isPresent);
    if (ratePlanCharges.length > 0) {
      const ratePlanChargeCollectionIdentifiers = ratePlanChargeCollection.map(
        ratePlanChargeItem => getRatePlanChargeBillingIdentifier(ratePlanChargeItem)!
      );
      const ratePlanChargesToAdd = ratePlanCharges.filter(ratePlanChargeItem => {
        const ratePlanChargeIdentifier = getRatePlanChargeBillingIdentifier(ratePlanChargeItem);
        if (ratePlanChargeIdentifier == null || ratePlanChargeCollectionIdentifiers.includes(ratePlanChargeIdentifier)) {
          return false;
        }
        ratePlanChargeCollectionIdentifiers.push(ratePlanChargeIdentifier);
        return true;
      });
      return [...ratePlanChargesToAdd, ...ratePlanChargeCollection];
    }
    return ratePlanChargeCollection;
  }
}
