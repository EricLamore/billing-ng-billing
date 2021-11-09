jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAddressBilling, AddressBilling } from '../address-billing.model';
import { AddressBillingService } from '../service/address-billing.service';

import { AddressBillingRoutingResolveService } from './address-billing-routing-resolve.service';

describe('AddressBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AddressBillingRoutingResolveService;
  let service: AddressBillingService;
  let resultAddressBilling: IAddressBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(AddressBillingRoutingResolveService);
    service = TestBed.inject(AddressBillingService);
    resultAddressBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IAddressBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAddressBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultAddressBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IAddressBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAddressBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAddressBilling).toEqual(new AddressBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AddressBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAddressBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultAddressBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
