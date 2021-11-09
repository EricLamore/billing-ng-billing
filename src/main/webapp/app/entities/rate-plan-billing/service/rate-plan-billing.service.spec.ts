import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRatePlanBilling, RatePlanBilling } from '../rate-plan-billing.model';

import { RatePlanBillingService } from './rate-plan-billing.service';

describe('RatePlanBilling Service', () => {
  let service: RatePlanBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IRatePlanBilling;
  let expectedResult: IRatePlanBilling | IRatePlanBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RatePlanBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      lastUpdate: currentDate,
      description: 'AAAAAAA',
      commercialName: 'AAAAAAA',
      base: 0,
      product: 'AAAAAAA',
      productRatePlanCharge: 'AAAAAAA',
      features: 'AAAAAAA',
      version: 'AAAAAAA',
      fixedPrice: false,
      standardModel: false,
      unitsIncluded: 0,
      unitsIncludedPrice: 0,
      unitsIncludedDuration: 0,
      ratePlanId: 'AAAAAAA',
      discountUnitPrice: 0,
      discountBase: 0,
      prorata: 0,
      activationDate: currentDate,
      endDate: currentDate,
      ratePlanCharges: 'AAAAAAA',
      subscriptionFeatures: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastUpdate: currentDate.format(DATE_FORMAT),
          activationDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a RatePlanBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          lastUpdate: currentDate.format(DATE_FORMAT),
          activationDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
          activationDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.create(new RatePlanBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RatePlanBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          commercialName: 'BBBBBB',
          base: 1,
          product: 'BBBBBB',
          productRatePlanCharge: 'BBBBBB',
          features: 'BBBBBB',
          version: 'BBBBBB',
          fixedPrice: true,
          standardModel: true,
          unitsIncluded: 1,
          unitsIncludedPrice: 1,
          unitsIncludedDuration: 1,
          ratePlanId: 'BBBBBB',
          discountUnitPrice: 1,
          discountBase: 1,
          prorata: 1,
          activationDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          ratePlanCharges: 'BBBBBB',
          subscriptionFeatures: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
          activationDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RatePlanBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          commercialName: 'BBBBBB',
          productRatePlanCharge: 'BBBBBB',
          features: 'BBBBBB',
          unitsIncluded: 1,
          unitsIncludedPrice: 1,
          discountBase: 1,
          activationDate: currentDate.format(DATE_FORMAT),
        },
        new RatePlanBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
          activationDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RatePlanBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          commercialName: 'BBBBBB',
          base: 1,
          product: 'BBBBBB',
          productRatePlanCharge: 'BBBBBB',
          features: 'BBBBBB',
          version: 'BBBBBB',
          fixedPrice: true,
          standardModel: true,
          unitsIncluded: 1,
          unitsIncludedPrice: 1,
          unitsIncludedDuration: 1,
          ratePlanId: 'BBBBBB',
          discountUnitPrice: 1,
          discountBase: 1,
          prorata: 1,
          activationDate: currentDate.format(DATE_FORMAT),
          endDate: currentDate.format(DATE_FORMAT),
          ratePlanCharges: 'BBBBBB',
          subscriptionFeatures: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
          activationDate: currentDate,
          endDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a RatePlanBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRatePlanBillingToCollectionIfMissing', () => {
      it('should add a RatePlanBilling to an empty array', () => {
        const ratePlan: IRatePlanBilling = { id: 'ABC' };
        expectedResult = service.addRatePlanBillingToCollectionIfMissing([], ratePlan);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ratePlan);
      });

      it('should not add a RatePlanBilling to an array that contains it', () => {
        const ratePlan: IRatePlanBilling = { id: 'ABC' };
        const ratePlanCollection: IRatePlanBilling[] = [
          {
            ...ratePlan,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addRatePlanBillingToCollectionIfMissing(ratePlanCollection, ratePlan);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RatePlanBilling to an array that doesn't contain it", () => {
        const ratePlan: IRatePlanBilling = { id: 'ABC' };
        const ratePlanCollection: IRatePlanBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addRatePlanBillingToCollectionIfMissing(ratePlanCollection, ratePlan);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ratePlan);
      });

      it('should add only unique RatePlanBilling to an array', () => {
        const ratePlanArray: IRatePlanBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'f4f4b9e5-ef18-4254-9385-02f0e5cdbc41' }];
        const ratePlanCollection: IRatePlanBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addRatePlanBillingToCollectionIfMissing(ratePlanCollection, ...ratePlanArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ratePlan: IRatePlanBilling = { id: 'ABC' };
        const ratePlan2: IRatePlanBilling = { id: 'CBA' };
        expectedResult = service.addRatePlanBillingToCollectionIfMissing([], ratePlan, ratePlan2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ratePlan);
        expect(expectedResult).toContain(ratePlan2);
      });

      it('should accept null and undefined values', () => {
        const ratePlan: IRatePlanBilling = { id: 'ABC' };
        expectedResult = service.addRatePlanBillingToCollectionIfMissing([], null, ratePlan, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ratePlan);
      });

      it('should return initial array if no RatePlanBilling is added', () => {
        const ratePlanCollection: IRatePlanBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addRatePlanBillingToCollectionIfMissing(ratePlanCollection, undefined, null);
        expect(expectedResult).toEqual(ratePlanCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
