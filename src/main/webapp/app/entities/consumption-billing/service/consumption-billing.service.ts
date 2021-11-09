import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConsumptionBilling, getConsumptionBillingIdentifier } from '../consumption-billing.model';

export type EntityResponseType = HttpResponse<IConsumptionBilling>;
export type EntityArrayResponseType = HttpResponse<IConsumptionBilling[]>;

@Injectable({ providedIn: 'root' })
export class ConsumptionBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/consumptions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(consumption: IConsumptionBilling): Observable<EntityResponseType> {
    return this.http.post<IConsumptionBilling>(this.resourceUrl, consumption, { observe: 'response' });
  }

  update(consumption: IConsumptionBilling): Observable<EntityResponseType> {
    return this.http.put<IConsumptionBilling>(
      `${this.resourceUrl}/${getConsumptionBillingIdentifier(consumption) as string}`,
      consumption,
      { observe: 'response' }
    );
  }

  partialUpdate(consumption: IConsumptionBilling): Observable<EntityResponseType> {
    return this.http.patch<IConsumptionBilling>(
      `${this.resourceUrl}/${getConsumptionBillingIdentifier(consumption) as string}`,
      consumption,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IConsumptionBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConsumptionBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConsumptionBillingToCollectionIfMissing(
    consumptionCollection: IConsumptionBilling[],
    ...consumptionsToCheck: (IConsumptionBilling | null | undefined)[]
  ): IConsumptionBilling[] {
    const consumptions: IConsumptionBilling[] = consumptionsToCheck.filter(isPresent);
    if (consumptions.length > 0) {
      const consumptionCollectionIdentifiers = consumptionCollection.map(
        consumptionItem => getConsumptionBillingIdentifier(consumptionItem)!
      );
      const consumptionsToAdd = consumptions.filter(consumptionItem => {
        const consumptionIdentifier = getConsumptionBillingIdentifier(consumptionItem);
        if (consumptionIdentifier == null || consumptionCollectionIdentifiers.includes(consumptionIdentifier)) {
          return false;
        }
        consumptionCollectionIdentifiers.push(consumptionIdentifier);
        return true;
      });
      return [...consumptionsToAdd, ...consumptionCollection];
    }
    return consumptionCollection;
  }
}
