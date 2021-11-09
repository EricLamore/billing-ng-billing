jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPaymentMethodBilling, PaymentMethodBilling } from '../payment-method-billing.model';
import { PaymentMethodBillingService } from '../service/payment-method-billing.service';

import { PaymentMethodBillingRoutingResolveService } from './payment-method-billing-routing-resolve.service';

describe('PaymentMethodBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PaymentMethodBillingRoutingResolveService;
  let service: PaymentMethodBillingService;
  let resultPaymentMethodBilling: IPaymentMethodBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PaymentMethodBillingRoutingResolveService);
    service = TestBed.inject(PaymentMethodBillingService);
    resultPaymentMethodBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IPaymentMethodBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentMethodBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultPaymentMethodBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IPaymentMethodBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentMethodBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPaymentMethodBilling).toEqual(new PaymentMethodBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PaymentMethodBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaymentMethodBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultPaymentMethodBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
