jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPersonRefererBilling, PersonRefererBilling } from '../person-referer-billing.model';
import { PersonRefererBillingService } from '../service/person-referer-billing.service';

import { PersonRefererBillingRoutingResolveService } from './person-referer-billing-routing-resolve.service';

describe('PersonRefererBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PersonRefererBillingRoutingResolveService;
  let service: PersonRefererBillingService;
  let resultPersonRefererBilling: IPersonRefererBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PersonRefererBillingRoutingResolveService);
    service = TestBed.inject(PersonRefererBillingService);
    resultPersonRefererBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IPersonRefererBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPersonRefererBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultPersonRefererBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IPersonRefererBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPersonRefererBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPersonRefererBilling).toEqual(new PersonRefererBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PersonRefererBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPersonRefererBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultPersonRefererBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
