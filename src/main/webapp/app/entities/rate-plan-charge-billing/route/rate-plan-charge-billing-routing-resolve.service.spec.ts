jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRatePlanChargeBilling, RatePlanChargeBilling } from '../rate-plan-charge-billing.model';
import { RatePlanChargeBillingService } from '../service/rate-plan-charge-billing.service';

import { RatePlanChargeBillingRoutingResolveService } from './rate-plan-charge-billing-routing-resolve.service';

describe('RatePlanChargeBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RatePlanChargeBillingRoutingResolveService;
  let service: RatePlanChargeBillingService;
  let resultRatePlanChargeBilling: IRatePlanChargeBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(RatePlanChargeBillingRoutingResolveService);
    service = TestBed.inject(RatePlanChargeBillingService);
    resultRatePlanChargeBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IRatePlanChargeBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRatePlanChargeBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultRatePlanChargeBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IRatePlanChargeBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRatePlanChargeBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRatePlanChargeBilling).toEqual(new RatePlanChargeBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RatePlanChargeBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRatePlanChargeBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultRatePlanChargeBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
