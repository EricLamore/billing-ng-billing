import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductSettingBilling, ProductSettingBilling } from '../product-setting-billing.model';

import { ProductSettingBillingService } from './product-setting-billing.service';

describe('ProductSettingBilling Service', () => {
  let service: ProductSettingBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductSettingBilling;
  let expectedResult: IProductSettingBilling | IProductSettingBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductSettingBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      value: 'AAAAAAA',
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

    it('should create a ProductSettingBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ProductSettingBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductSettingBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          value: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductSettingBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          value: 'BBBBBB',
        },
        new ProductSettingBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductSettingBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          value: 'BBBBBB',
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

    it('should delete a ProductSettingBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductSettingBillingToCollectionIfMissing', () => {
      it('should add a ProductSettingBilling to an empty array', () => {
        const productSetting: IProductSettingBilling = { id: 'ABC' };
        expectedResult = service.addProductSettingBillingToCollectionIfMissing([], productSetting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSetting);
      });

      it('should not add a ProductSettingBilling to an array that contains it', () => {
        const productSetting: IProductSettingBilling = { id: 'ABC' };
        const productSettingCollection: IProductSettingBilling[] = [
          {
            ...productSetting,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addProductSettingBillingToCollectionIfMissing(productSettingCollection, productSetting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductSettingBilling to an array that doesn't contain it", () => {
        const productSetting: IProductSettingBilling = { id: 'ABC' };
        const productSettingCollection: IProductSettingBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addProductSettingBillingToCollectionIfMissing(productSettingCollection, productSetting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSetting);
      });

      it('should add only unique ProductSettingBilling to an array', () => {
        const productSettingArray: IProductSettingBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '80f74d43-a968-4bd1-97ca-c5259c77c0a4' },
        ];
        const productSettingCollection: IProductSettingBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductSettingBillingToCollectionIfMissing(productSettingCollection, ...productSettingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productSetting: IProductSettingBilling = { id: 'ABC' };
        const productSetting2: IProductSettingBilling = { id: 'CBA' };
        expectedResult = service.addProductSettingBillingToCollectionIfMissing([], productSetting, productSetting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productSetting);
        expect(expectedResult).toContain(productSetting2);
      });

      it('should accept null and undefined values', () => {
        const productSetting: IProductSettingBilling = { id: 'ABC' };
        expectedResult = service.addProductSettingBillingToCollectionIfMissing([], null, productSetting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productSetting);
      });

      it('should return initial array if no ProductSettingBilling is added', () => {
        const productSettingCollection: IProductSettingBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addProductSettingBillingToCollectionIfMissing(productSettingCollection, undefined, null);
        expect(expectedResult).toEqual(productSettingCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
