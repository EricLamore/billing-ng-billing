import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductRatePlanBilling, ProductRatePlanBilling } from '../product-rate-plan-billing.model';

import { ProductRatePlanBillingService } from './product-rate-plan-billing.service';

describe('ProductRatePlanBilling Service', () => {
  let service: ProductRatePlanBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductRatePlanBilling;
  let expectedResult: IProductRatePlanBilling | IProductRatePlanBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductRatePlanBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
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

    it('should create a ProductRatePlanBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ProductRatePlanBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductRatePlanBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
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
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductRatePlanBilling', () => {
      const patchObject = Object.assign(
        {
          base: 1,
          product: 'BBBBBB',
          features: 'BBBBBB',
          version: 'BBBBBB',
          standardModel: true,
        },
        new ProductRatePlanBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductRatePlanBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
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

    it('should delete a ProductRatePlanBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductRatePlanBillingToCollectionIfMissing', () => {
      it('should add a ProductRatePlanBilling to an empty array', () => {
        const productRatePlan: IProductRatePlanBilling = { id: 'ABC' };
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing([], productRatePlan);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productRatePlan);
      });

      it('should not add a ProductRatePlanBilling to an array that contains it', () => {
        const productRatePlan: IProductRatePlanBilling = { id: 'ABC' };
        const productRatePlanCollection: IProductRatePlanBilling[] = [
          {
            ...productRatePlan,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing(productRatePlanCollection, productRatePlan);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductRatePlanBilling to an array that doesn't contain it", () => {
        const productRatePlan: IProductRatePlanBilling = { id: 'ABC' };
        const productRatePlanCollection: IProductRatePlanBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing(productRatePlanCollection, productRatePlan);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productRatePlan);
      });

      it('should add only unique ProductRatePlanBilling to an array', () => {
        const productRatePlanArray: IProductRatePlanBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '8b7cb061-5f25-435f-9e5c-0c09592149d4' },
        ];
        const productRatePlanCollection: IProductRatePlanBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing(productRatePlanCollection, ...productRatePlanArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productRatePlan: IProductRatePlanBilling = { id: 'ABC' };
        const productRatePlan2: IProductRatePlanBilling = { id: 'CBA' };
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing([], productRatePlan, productRatePlan2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productRatePlan);
        expect(expectedResult).toContain(productRatePlan2);
      });

      it('should accept null and undefined values', () => {
        const productRatePlan: IProductRatePlanBilling = { id: 'ABC' };
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing([], null, productRatePlan, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productRatePlan);
      });

      it('should return initial array if no ProductRatePlanBilling is added', () => {
        const productRatePlanCollection: IProductRatePlanBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductRatePlanBillingToCollectionIfMissing(productRatePlanCollection, undefined, null);
        expect(expectedResult).toEqual(productRatePlanCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
