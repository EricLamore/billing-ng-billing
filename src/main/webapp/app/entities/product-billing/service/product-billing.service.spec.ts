import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { TypeProduct } from 'app/entities/enumerations/type-product.model';
import { IProductBilling, ProductBilling } from '../product-billing.model';

import { ProductBillingService } from './product-billing.service';

describe('ProductBilling Service', () => {
  let service: ProductBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductBilling;
  let expectedResult: IProductBilling | IProductBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      lastUpdate: currentDate,
      description: 'AAAAAAA',
      certificateTypes: 'AAAAAAA',
      commercialName: 'AAAAAAA',
      productType: TypeProduct.SIGNATURE,
      settings: 'AAAAAAA',
      matrix: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          lastUpdate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ProductBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          lastUpdate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.create(new ProductBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          certificateTypes: 'BBBBBB',
          commercialName: 'BBBBBB',
          productType: 'BBBBBB',
          settings: 'BBBBBB',
          matrix: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          description: 'BBBBBB',
          commercialName: 'BBBBBB',
          productType: 'BBBBBB',
          settings: 'BBBBBB',
          matrix: 1,
        },
        new ProductBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          certificateTypes: 'BBBBBB',
          commercialName: 'BBBBBB',
          productType: 'BBBBBB',
          settings: 'BBBBBB',
          matrix: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          lastUpdate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ProductBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductBillingToCollectionIfMissing', () => {
      it('should add a ProductBilling to an empty array', () => {
        const product: IProductBilling = { id: 'ABC' };
        expectedResult = service.addProductBillingToCollectionIfMissing([], product);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(product);
      });

      it('should not add a ProductBilling to an array that contains it', () => {
        const product: IProductBilling = { id: 'ABC' };
        const productCollection: IProductBilling[] = [
          {
            ...product,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addProductBillingToCollectionIfMissing(productCollection, product);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductBilling to an array that doesn't contain it", () => {
        const product: IProductBilling = { id: 'ABC' };
        const productCollection: IProductBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addProductBillingToCollectionIfMissing(productCollection, product);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(product);
      });

      it('should add only unique ProductBilling to an array', () => {
        const productArray: IProductBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '2d3c9be1-2ff8-4d11-ad21-b5eb9865b473' }];
        const productCollection: IProductBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductBillingToCollectionIfMissing(productCollection, ...productArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const product: IProductBilling = { id: 'ABC' };
        const product2: IProductBilling = { id: 'CBA' };
        expectedResult = service.addProductBillingToCollectionIfMissing([], product, product2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(product);
        expect(expectedResult).toContain(product2);
      });

      it('should accept null and undefined values', () => {
        const product: IProductBilling = { id: 'ABC' };
        expectedResult = service.addProductBillingToCollectionIfMissing([], null, product, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(product);
      });

      it('should return initial array if no ProductBilling is added', () => {
        const productCollection: IProductBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductBillingToCollectionIfMissing(productCollection, undefined, null);
        expect(expectedResult).toEqual(productCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
