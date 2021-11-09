jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IInvoiceBilling, InvoiceBilling } from '../invoice-billing.model';
import { InvoiceBillingService } from '../service/invoice-billing.service';

import { InvoiceBillingRoutingResolveService } from './invoice-billing-routing-resolve.service';

describe('InvoiceBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InvoiceBillingRoutingResolveService;
  let service: InvoiceBillingService;
  let resultInvoiceBilling: IInvoiceBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(InvoiceBillingRoutingResolveService);
    service = TestBed.inject(InvoiceBillingService);
    resultInvoiceBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IInvoiceBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInvoiceBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultInvoiceBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IInvoiceBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInvoiceBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInvoiceBilling).toEqual(new InvoiceBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InvoiceBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInvoiceBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultInvoiceBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
