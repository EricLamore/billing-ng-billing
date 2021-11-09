jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISettingsInvoiceBilling, SettingsInvoiceBilling } from '../settings-invoice-billing.model';
import { SettingsInvoiceBillingService } from '../service/settings-invoice-billing.service';

import { SettingsInvoiceBillingRoutingResolveService } from './settings-invoice-billing-routing-resolve.service';

describe('SettingsInvoiceBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SettingsInvoiceBillingRoutingResolveService;
  let service: SettingsInvoiceBillingService;
  let resultSettingsInvoiceBilling: ISettingsInvoiceBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SettingsInvoiceBillingRoutingResolveService);
    service = TestBed.inject(SettingsInvoiceBillingService);
    resultSettingsInvoiceBilling = undefined;
  });

  describe('resolve', () => {
    it('should return ISettingsInvoiceBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsInvoiceBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSettingsInvoiceBilling).toEqual({ id: 'ABC' });
    });

    it('should return new ISettingsInvoiceBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsInvoiceBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSettingsInvoiceBilling).toEqual(new SettingsInvoiceBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SettingsInvoiceBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsInvoiceBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSettingsInvoiceBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
