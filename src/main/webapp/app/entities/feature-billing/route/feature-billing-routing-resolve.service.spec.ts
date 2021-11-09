jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFeatureBilling, FeatureBilling } from '../feature-billing.model';
import { FeatureBillingService } from '../service/feature-billing.service';

import { FeatureBillingRoutingResolveService } from './feature-billing-routing-resolve.service';

describe('FeatureBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: FeatureBillingRoutingResolveService;
  let service: FeatureBillingService;
  let resultFeatureBilling: IFeatureBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(FeatureBillingRoutingResolveService);
    service = TestBed.inject(FeatureBillingService);
    resultFeatureBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IFeatureBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFeatureBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultFeatureBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IFeatureBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFeatureBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFeatureBilling).toEqual(new FeatureBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FeatureBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultFeatureBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultFeatureBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
