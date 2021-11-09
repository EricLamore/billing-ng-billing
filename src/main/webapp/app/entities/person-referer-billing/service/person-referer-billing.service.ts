import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonRefererBilling, getPersonRefererBillingIdentifier } from '../person-referer-billing.model';

export type EntityResponseType = HttpResponse<IPersonRefererBilling>;
export type EntityArrayResponseType = HttpResponse<IPersonRefererBilling[]>;

@Injectable({ providedIn: 'root' })
export class PersonRefererBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/person-referers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(personReferer: IPersonRefererBilling): Observable<EntityResponseType> {
    return this.http.post<IPersonRefererBilling>(this.resourceUrl, personReferer, { observe: 'response' });
  }

  update(personReferer: IPersonRefererBilling): Observable<EntityResponseType> {
    return this.http.put<IPersonRefererBilling>(
      `${this.resourceUrl}/${getPersonRefererBillingIdentifier(personReferer) as string}`,
      personReferer,
      { observe: 'response' }
    );
  }

  partialUpdate(personReferer: IPersonRefererBilling): Observable<EntityResponseType> {
    return this.http.patch<IPersonRefererBilling>(
      `${this.resourceUrl}/${getPersonRefererBillingIdentifier(personReferer) as string}`,
      personReferer,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPersonRefererBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonRefererBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPersonRefererBillingToCollectionIfMissing(
    personRefererCollection: IPersonRefererBilling[],
    ...personReferersToCheck: (IPersonRefererBilling | null | undefined)[]
  ): IPersonRefererBilling[] {
    const personReferers: IPersonRefererBilling[] = personReferersToCheck.filter(isPresent);
    if (personReferers.length > 0) {
      const personRefererCollectionIdentifiers = personRefererCollection.map(
        personRefererItem => getPersonRefererBillingIdentifier(personRefererItem)!
      );
      const personReferersToAdd = personReferers.filter(personRefererItem => {
        const personRefererIdentifier = getPersonRefererBillingIdentifier(personRefererItem);
        if (personRefererIdentifier == null || personRefererCollectionIdentifiers.includes(personRefererIdentifier)) {
          return false;
        }
        personRefererCollectionIdentifiers.push(personRefererIdentifier);
        return true;
      });
      return [...personReferersToAdd, ...personRefererCollection];
    }
    return personRefererCollection;
  }
}
