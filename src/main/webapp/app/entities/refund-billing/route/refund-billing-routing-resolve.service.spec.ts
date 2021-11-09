jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRefundBilling, RefundBilling } from '../refund-billing.model';
import { RefundBillingService } from '../service/refund-billing.service';

import { RefundBillingRoutingResolveService } from './refund-billing-routing-resolve.service';

describe('RefundBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RefundBillingRoutingResolveService;
  let service: RefundBillingService;
  let resultRefundBilling: IRefundBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(RefundBillingRoutingResolveService);
    service = TestBed.inject(RefundBillingService);
    resultRefundBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IRefundBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRefundBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultRefundBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IRefundBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRefundBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRefundBilling).toEqual(new RefundBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RefundBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRefundBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultRefundBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
