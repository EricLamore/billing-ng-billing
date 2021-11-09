import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRatePlanChargeBilling, RatePlanChargeBilling } from '../rate-plan-charge-billing.model';

import { RatePlanChargeBillingService } from './rate-plan-charge-billing.service';

describe('RatePlanChargeBilling Service', () => {
  let service: RatePlanChargeBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IRatePlanChargeBilling;
  let expectedResult: IRatePlanChargeBilling | IRatePlanChargeBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RatePlanChargeBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      step: 0,
      unitPrice: 0,
      discount: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a RatePlanChargeBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RatePlanChargeBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RatePlanChargeBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          step: 1,
          unitPrice: 1,
          discount: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RatePlanChargeBilling', () => {
      const patchObject = Object.assign(
        {
          step: 1,
          unitPrice: 1,
        },
        new RatePlanChargeBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RatePlanChargeBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          step: 1,
          unitPrice: 1,
          discount: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a RatePlanChargeBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRatePlanChargeBillingToCollectionIfMissing', () => {
      it('should add a RatePlanChargeBilling to an empty array', () => {
        const ratePlanCharge: IRatePlanChargeBilling = { id: 'ABC' };
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing([], ratePlanCharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ratePlanCharge);
      });

      it('should not add a RatePlanChargeBilling to an array that contains it', () => {
        const ratePlanCharge: IRatePlanChargeBilling = { id: 'ABC' };
        const ratePlanChargeCollection: IRatePlanChargeBilling[] = [
          {
            ...ratePlanCharge,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing(ratePlanChargeCollection, ratePlanCharge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RatePlanChargeBilling to an array that doesn't contain it", () => {
        const ratePlanCharge: IRatePlanChargeBilling = { id: 'ABC' };
        const ratePlanChargeCollection: IRatePlanChargeBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing(ratePlanChargeCollection, ratePlanCharge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ratePlanCharge);
      });

      it('should add only unique RatePlanChargeBilling to an array', () => {
        const ratePlanChargeArray: IRatePlanChargeBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '985deb95-4c11-4510-a533-0b9e4b56b376' },
        ];
        const ratePlanChargeCollection: IRatePlanChargeBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing(ratePlanChargeCollection, ...ratePlanChargeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ratePlanCharge: IRatePlanChargeBilling = { id: 'ABC' };
        const ratePlanCharge2: IRatePlanChargeBilling = { id: 'CBA' };
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing([], ratePlanCharge, ratePlanCharge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ratePlanCharge);
        expect(expectedResult).toContain(ratePlanCharge2);
      });

      it('should accept null and undefined values', () => {
        const ratePlanCharge: IRatePlanChargeBilling = { id: 'ABC' };
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing([], null, ratePlanCharge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ratePlanCharge);
      });

      it('should return initial array if no RatePlanChargeBilling is added', () => {
        const ratePlanChargeCollection: IRatePlanChargeBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addRatePlanChargeBillingToCollectionIfMissing(ratePlanChargeCollection, undefined, null);
        expect(expectedResult).toEqual(ratePlanChargeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
