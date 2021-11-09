jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRatePlanBilling, RatePlanBilling } from '../rate-plan-billing.model';
import { RatePlanBillingService } from '../service/rate-plan-billing.service';

import { RatePlanBillingRoutingResolveService } from './rate-plan-billing-routing-resolve.service';

describe('RatePlanBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RatePlanBillingRoutingResolveService;
  let service: RatePlanBillingService;
  let resultRatePlanBilling: IRatePlanBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(RatePlanBillingRoutingResolveService);
    service = TestBed.inject(RatePlanBillingService);
    resultRatePlanBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IRatePlanBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRatePlanBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultRatePlanBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IRatePlanBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRatePlanBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRatePlanBilling).toEqual(new RatePlanBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RatePlanBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRatePlanBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultRatePlanBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
