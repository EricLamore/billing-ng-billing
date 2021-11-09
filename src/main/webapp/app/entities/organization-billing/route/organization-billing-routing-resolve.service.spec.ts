jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOrganizationBilling, OrganizationBilling } from '../organization-billing.model';
import { OrganizationBillingService } from '../service/organization-billing.service';

import { OrganizationBillingRoutingResolveService } from './organization-billing-routing-resolve.service';

describe('OrganizationBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: OrganizationBillingRoutingResolveService;
  let service: OrganizationBillingService;
  let resultOrganizationBilling: IOrganizationBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(OrganizationBillingRoutingResolveService);
    service = TestBed.inject(OrganizationBillingService);
    resultOrganizationBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IOrganizationBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultOrganizationBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IOrganizationBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOrganizationBilling).toEqual(new OrganizationBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OrganizationBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultOrganizationBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultOrganizationBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
