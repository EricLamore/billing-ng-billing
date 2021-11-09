jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProductRatePlanChargeBilling, ProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';
import { ProductRatePlanChargeBillingService } from '../service/product-rate-plan-charge-billing.service';

import { ProductRatePlanChargeBillingRoutingResolveService } from './product-rate-plan-charge-billing-routing-resolve.service';

describe('ProductRatePlanChargeBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductRatePlanChargeBillingRoutingResolveService;
  let service: ProductRatePlanChargeBillingService;
  let resultProductRatePlanChargeBilling: IProductRatePlanChargeBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ProductRatePlanChargeBillingRoutingResolveService);
    service = TestBed.inject(ProductRatePlanChargeBillingService);
    resultProductRatePlanChargeBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IProductRatePlanChargeBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductRatePlanChargeBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductRatePlanChargeBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IProductRatePlanChargeBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductRatePlanChargeBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductRatePlanChargeBilling).toEqual(new ProductRatePlanChargeBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProductRatePlanChargeBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductRatePlanChargeBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductRatePlanChargeBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
