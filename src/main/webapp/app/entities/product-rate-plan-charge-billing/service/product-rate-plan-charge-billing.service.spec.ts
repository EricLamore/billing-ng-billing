import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductRatePlanChargeBilling, ProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';

import { ProductRatePlanChargeBillingService } from './product-rate-plan-charge-billing.service';

describe('ProductRatePlanChargeBilling Service', () => {
  let service: ProductRatePlanChargeBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductRatePlanChargeBilling;
  let expectedResult: IProductRatePlanChargeBilling | IProductRatePlanChargeBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductRatePlanChargeBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      step: 0,
      unitPrice: 0,
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

    it('should create a ProductRatePlanChargeBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ProductRatePlanChargeBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductRatePlanChargeBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          step: 1,
          unitPrice: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductRatePlanChargeBilling', () => {
      const patchObject = Object.assign(
        {
          step: 1,
          unitPrice: 1,
        },
        new ProductRatePlanChargeBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductRatePlanChargeBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          step: 1,
          unitPrice: 1,
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

    it('should delete a ProductRatePlanChargeBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductRatePlanChargeBillingToCollectionIfMissing', () => {
      it('should add a ProductRatePlanChargeBilling to an empty array', () => {
        const productRatePlanCharge: IProductRatePlanChargeBilling = { id: 'ABC' };
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing([], productRatePlanCharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productRatePlanCharge);
      });

      it('should not add a ProductRatePlanChargeBilling to an array that contains it', () => {
        const productRatePlanCharge: IProductRatePlanChargeBilling = { id: 'ABC' };
        const productRatePlanChargeCollection: IProductRatePlanChargeBilling[] = [
          {
            ...productRatePlanCharge,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing(
          productRatePlanChargeCollection,
          productRatePlanCharge
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductRatePlanChargeBilling to an array that doesn't contain it", () => {
        const productRatePlanCharge: IProductRatePlanChargeBilling = { id: 'ABC' };
        const productRatePlanChargeCollection: IProductRatePlanChargeBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing(
          productRatePlanChargeCollection,
          productRatePlanCharge
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productRatePlanCharge);
      });

      it('should add only unique ProductRatePlanChargeBilling to an array', () => {
        const productRatePlanChargeArray: IProductRatePlanChargeBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '690282a6-c42d-4406-81eb-8176ef9af3f6' },
        ];
        const productRatePlanChargeCollection: IProductRatePlanChargeBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing(
          productRatePlanChargeCollection,
          ...productRatePlanChargeArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productRatePlanCharge: IProductRatePlanChargeBilling = { id: 'ABC' };
        const productRatePlanCharge2: IProductRatePlanChargeBilling = { id: 'CBA' };
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing([], productRatePlanCharge, productRatePlanCharge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productRatePlanCharge);
        expect(expectedResult).toContain(productRatePlanCharge2);
      });

      it('should accept null and undefined values', () => {
        const productRatePlanCharge: IProductRatePlanChargeBilling = { id: 'ABC' };
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing([], null, productRatePlanCharge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productRatePlanCharge);
      });

      it('should return initial array if no ProductRatePlanChargeBilling is added', () => {
        const productRatePlanChargeCollection: IProductRatePlanChargeBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductRatePlanChargeBillingToCollectionIfMissing(productRatePlanChargeCollection, undefined, null);
        expect(expectedResult).toEqual(productRatePlanChargeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
