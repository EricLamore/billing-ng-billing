import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductSettingBilling, getProductSettingBillingIdentifier } from '../product-setting-billing.model';

export type EntityResponseType = HttpResponse<IProductSettingBilling>;
export type EntityArrayResponseType = HttpResponse<IProductSettingBilling[]>;

@Injectable({ providedIn: 'root' })
export class ProductSettingBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productSetting: IProductSettingBilling): Observable<EntityResponseType> {
    return this.http.post<IProductSettingBilling>(this.resourceUrl, productSetting, { observe: 'response' });
  }

  update(productSetting: IProductSettingBilling): Observable<EntityResponseType> {
    return this.http.put<IProductSettingBilling>(
      `${this.resourceUrl}/${getProductSettingBillingIdentifier(productSetting) as string}`,
      productSetting,
      { observe: 'response' }
    );
  }

  partialUpdate(productSetting: IProductSettingBilling): Observable<EntityResponseType> {
    return this.http.patch<IProductSettingBilling>(
      `${this.resourceUrl}/${getProductSettingBillingIdentifier(productSetting) as string}`,
      productSetting,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProductSettingBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductSettingBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductSettingBillingToCollectionIfMissing(
    productSettingCollection: IProductSettingBilling[],
    ...productSettingsToCheck: (IProductSettingBilling | null | undefined)[]
  ): IProductSettingBilling[] {
    const productSettings: IProductSettingBilling[] = productSettingsToCheck.filter(isPresent);
    if (productSettings.length > 0) {
      const productSettingCollectionIdentifiers = productSettingCollection.map(
        productSettingItem => getProductSettingBillingIdentifier(productSettingItem)!
      );
      const productSettingsToAdd = productSettings.filter(productSettingItem => {
        const productSettingIdentifier = getProductSettingBillingIdentifier(productSettingItem);
        if (productSettingIdentifier == null || productSettingCollectionIdentifiers.includes(productSettingIdentifier)) {
          return false;
        }
        productSettingCollectionIdentifiers.push(productSettingIdentifier);
        return true;
      });
      return [...productSettingsToAdd, ...productSettingCollection];
    }
    return productSettingCollection;
  }
}
