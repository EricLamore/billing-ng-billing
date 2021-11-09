jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProductRatePlanBilling, ProductRatePlanBilling } from '../product-rate-plan-billing.model';
import { ProductRatePlanBillingService } from '../service/product-rate-plan-billing.service';

import { ProductRatePlanBillingRoutingResolveService } from './product-rate-plan-billing-routing-resolve.service';

describe('ProductRatePlanBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductRatePlanBillingRoutingResolveService;
  let service: ProductRatePlanBillingService;
  let resultProductRatePlanBilling: IProductRatePlanBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ProductRatePlanBillingRoutingResolveService);
    service = TestBed.inject(ProductRatePlanBillingService);
    resultProductRatePlanBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IProductRatePlanBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductRatePlanBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductRatePlanBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IProductRatePlanBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductRatePlanBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductRatePlanBilling).toEqual(new ProductRatePlanBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProductRatePlanBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductRatePlanBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductRatePlanBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
