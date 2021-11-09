jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IInvoiceItemBilling, InvoiceItemBilling } from '../invoice-item-billing.model';
import { InvoiceItemBillingService } from '../service/invoice-item-billing.service';

import { InvoiceItemBillingRoutingResolveService } from './invoice-item-billing-routing-resolve.service';

describe('InvoiceItemBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: InvoiceItemBillingRoutingResolveService;
  let service: InvoiceItemBillingService;
  let resultInvoiceItemBilling: IInvoiceItemBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(InvoiceItemBillingRoutingResolveService);
    service = TestBed.inject(InvoiceItemBillingService);
    resultInvoiceItemBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IInvoiceItemBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInvoiceItemBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultInvoiceItemBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IInvoiceItemBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInvoiceItemBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultInvoiceItemBilling).toEqual(new InvoiceItemBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as InvoiceItemBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultInvoiceItemBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultInvoiceItemBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
