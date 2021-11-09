import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAddressBilling, getAddressBillingIdentifier } from '../address-billing.model';

export type EntityResponseType = HttpResponse<IAddressBilling>;
export type EntityArrayResponseType = HttpResponse<IAddressBilling[]>;

@Injectable({ providedIn: 'root' })
export class AddressBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/addresses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(address: IAddressBilling): Observable<EntityResponseType> {
    return this.http.post<IAddressBilling>(this.resourceUrl, address, { observe: 'response' });
  }

  update(address: IAddressBilling): Observable<EntityResponseType> {
    return this.http.put<IAddressBilling>(`${this.resourceUrl}/${getAddressBillingIdentifier(address) as string}`, address, {
      observe: 'response',
    });
  }

  partialUpdate(address: IAddressBilling): Observable<EntityResponseType> {
    return this.http.patch<IAddressBilling>(`${this.resourceUrl}/${getAddressBillingIdentifier(address) as string}`, address, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAddressBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAddressBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAddressBillingToCollectionIfMissing(
    addressCollection: IAddressBilling[],
    ...addressesToCheck: (IAddressBilling | null | undefined)[]
  ): IAddressBilling[] {
    const addresses: IAddressBilling[] = addressesToCheck.filter(isPresent);
    if (addresses.length > 0) {
      const addressCollectionIdentifiers = addressCollection.map(addressItem => getAddressBillingIdentifier(addressItem)!);
      const addressesToAdd = addresses.filter(addressItem => {
        const addressIdentifier = getAddressBillingIdentifier(addressItem);
        if (addressIdentifier == null || addressCollectionIdentifiers.includes(addressIdentifier)) {
          return false;
        }
        addressCollectionIdentifiers.push(addressIdentifier);
        return true;
      });
      return [...addressesToAdd, ...addressCollection];
    }
    return addressCollection;
  }
}
