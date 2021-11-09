jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISubscriptionFeatureBilling, SubscriptionFeatureBilling } from '../subscription-feature-billing.model';
import { SubscriptionFeatureBillingService } from '../service/subscription-feature-billing.service';

import { SubscriptionFeatureBillingRoutingResolveService } from './subscription-feature-billing-routing-resolve.service';

describe('SubscriptionFeatureBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SubscriptionFeatureBillingRoutingResolveService;
  let service: SubscriptionFeatureBillingService;
  let resultSubscriptionFeatureBilling: ISubscriptionFeatureBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SubscriptionFeatureBillingRoutingResolveService);
    service = TestBed.inject(SubscriptionFeatureBillingService);
    resultSubscriptionFeatureBilling = undefined;
  });

  describe('resolve', () => {
    it('should return ISubscriptionFeatureBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubscriptionFeatureBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSubscriptionFeatureBilling).toEqual({ id: 'ABC' });
    });

    it('should return new ISubscriptionFeatureBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubscriptionFeatureBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubscriptionFeatureBilling).toEqual(new SubscriptionFeatureBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SubscriptionFeatureBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSubscriptionFeatureBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSubscriptionFeatureBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
