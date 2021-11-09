jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IProductBilling, ProductBilling } from '../product-billing.model';
import { ProductBillingService } from '../service/product-billing.service';

import { ProductBillingRoutingResolveService } from './product-billing-routing-resolve.service';

describe('ProductBilling routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProductBillingRoutingResolveService;
  let service: ProductBillingService;
  let resultProductBilling: IProductBilling | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ProductBillingRoutingResolveService);
    service = TestBed.inject(ProductBillingService);
    resultProductBilling = undefined;
  });

  describe('resolve', () => {
    it('should return IProductBilling returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductBilling).toEqual({ id: 'ABC' });
    });

    it('should return new IProductBilling if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductBilling = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProductBilling).toEqual(new ProductBilling());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ProductBilling })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProductBilling = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProductBilling).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
