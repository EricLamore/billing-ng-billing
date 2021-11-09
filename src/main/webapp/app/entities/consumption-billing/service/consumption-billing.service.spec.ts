import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TypeProduct } from 'app/entities/enumerations/type-product.model';
import { IConsumptionBilling, ConsumptionBilling } from '../consumption-billing.model';

import { ConsumptionBillingService } from './consumption-billing.service';

describe('ConsumptionBilling Service', () => {
  let service: ConsumptionBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IConsumptionBilling;
  let expectedResult: IConsumptionBilling | IConsumptionBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConsumptionBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      organisationId: 'AAAAAAA',
      organizationName: 'AAAAAAA',
      ratePlanId: 'AAAAAAA',
      name: 'AAAAAAA',
      type: TypeProduct.SIGNATURE,
      month: 0,
      year: 0,
      details: 'AAAAAAA',
      nbUnits: 0,
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

    it('should create a ConsumptionBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ConsumptionBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConsumptionBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          organisationId: 'BBBBBB',
          organizationName: 'BBBBBB',
          ratePlanId: 'BBBBBB',
          name: 'BBBBBB',
          type: 'BBBBBB',
          month: 1,
          year: 1,
          details: 'BBBBBB',
          nbUnits: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConsumptionBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          type: 'BBBBBB',
          month: 1,
          year: 1,
        },
        new ConsumptionBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConsumptionBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          organisationId: 'BBBBBB',
          organizationName: 'BBBBBB',
          ratePlanId: 'BBBBBB',
          name: 'BBBBBB',
          type: 'BBBBBB',
          month: 1,
          year: 1,
          details: 'BBBBBB',
          nbUnits: 1,
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

    it('should delete a ConsumptionBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addConsumptionBillingToCollectionIfMissing', () => {
      it('should add a ConsumptionBilling to an empty array', () => {
        const consumption: IConsumptionBilling = { id: 'ABC' };
        expectedResult = service.addConsumptionBillingToCollectionIfMissing([], consumption);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consumption);
      });

      it('should not add a ConsumptionBilling to an array that contains it', () => {
        const consumption: IConsumptionBilling = { id: 'ABC' };
        const consumptionCollection: IConsumptionBilling[] = [
          {
            ...consumption,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addConsumptionBillingToCollectionIfMissing(consumptionCollection, consumption);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConsumptionBilling to an array that doesn't contain it", () => {
        const consumption: IConsumptionBilling = { id: 'ABC' };
        const consumptionCollection: IConsumptionBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addConsumptionBillingToCollectionIfMissing(consumptionCollection, consumption);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consumption);
      });

      it('should add only unique ConsumptionBilling to an array', () => {
        const consumptionArray: IConsumptionBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'ce9bddee-897d-4ff0-9aad-e661d28f05f1' }];
        const consumptionCollection: IConsumptionBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addConsumptionBillingToCollectionIfMissing(consumptionCollection, ...consumptionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const consumption: IConsumptionBilling = { id: 'ABC' };
        const consumption2: IConsumptionBilling = { id: 'CBA' };
        expectedResult = service.addConsumptionBillingToCollectionIfMissing([], consumption, consumption2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consumption);
        expect(expectedResult).toContain(consumption2);
      });

      it('should accept null and undefined values', () => {
        const consumption: IConsumptionBilling = { id: 'ABC' };
        expectedResult = service.addConsumptionBillingToCollectionIfMissing([], null, consumption, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consumption);
      });

      it('should return initial array if no ConsumptionBilling is added', () => {
        const consumptionCollection: IConsumptionBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addConsumptionBillingToCollectionIfMissing(consumptionCollection, undefined, null);
        expect(expectedResult).toEqual(consumptionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
