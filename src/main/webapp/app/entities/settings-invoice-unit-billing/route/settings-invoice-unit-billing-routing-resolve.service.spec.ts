jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISettingsInvoiceUnitBilling, SettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';
import { SettingsInvoiceUnitBillingService } from '../service/settings-invoice-unit-billing.service';

import { SettingsInvoiceUnitBillingRoutingResolveService } from './settings-invoice-unit-billing-routing-resolve.service';

describe('SettingsInvoiceUnitBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SettingsInvoiceUnitBillingRoutingResolveService;
  let service: SettingsInvoiceUnitBillingService;
  let resultSettingsInvoiceUnitBilling: ISettingsInvoiceUnitBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(SettingsInvoiceUnitBillingRoutingResolveService);
    service = TestBed.inject(SettingsInvoiceUnitBillingService);
    resultSettingsInvoiceUnitBilling = undefined;
  });

  describe('resolve', () => {
    it('should return ISettingsInvoiceUnitBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsInvoiceUnitBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSettingsInvoiceUnitBilling).toEqual({ id: 'ABC' });
    });

    it('should return new ISettingsInvoiceUnitBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsInvoiceUnitBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSettingsInvoiceUnitBilling).toEqual(new SettingsInvoiceUnitBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SettingsInvoiceUnitBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSettingsInvoiceUnitBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultSettingsInvoiceUnitBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
