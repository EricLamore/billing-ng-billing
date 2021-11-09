jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProductSettingBilling, ProductSettingBilling } from '../product-setting-billing.model';
import { ProductSettingBillingService } from '../service/product-setting-billing.service';

import { ProductSettingBillingRoutingResolveService } from './product-setting-billing-routing-resolve.service';

describe('ProductSettingBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductSettingBillingRoutingResolveService;
  let service: ProductSettingBillingService;
  let resultProductSettingBilling: IProductSettingBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ProductSettingBillingRoutingResolveService);
    service = TestBed.inject(ProductSettingBillingService);
    resultProductSettingBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IProductSettingBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSettingBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSettingBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IProductSettingBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSettingBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductSettingBilling).toEqual(new ProductSettingBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProductSettingBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductSettingBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductSettingBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
