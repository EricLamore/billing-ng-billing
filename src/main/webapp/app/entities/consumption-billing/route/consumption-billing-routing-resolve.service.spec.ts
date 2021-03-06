jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IConsumptionBilling, ConsumptionBilling } from '../consumption-billing.model';
import { ConsumptionBillingService } from '../service/consumption-billing.service';

import { ConsumptionBillingRoutingResolveService } from './consumption-billing-routing-resolve.service';

describe('ConsumptionBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ConsumptionBillingRoutingResolveService;
  let service: ConsumptionBillingService;
  let resultConsumptionBilling: IConsumptionBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ConsumptionBillingRoutingResolveService);
    service = TestBed.inject(ConsumptionBillingService);
    resultConsumptionBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IConsumptionBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConsumptionBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultConsumptionBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IConsumptionBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConsumptionBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultConsumptionBilling).toEqual(new ConsumptionBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ConsumptionBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultConsumptionBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultConsumptionBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
