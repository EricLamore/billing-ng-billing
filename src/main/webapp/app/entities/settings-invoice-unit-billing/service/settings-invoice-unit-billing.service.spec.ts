import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISettingsInvoiceUnitBilling, SettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';

import { SettingsInvoiceUnitBillingService } from './settings-invoice-unit-billing.service';

describe('SettingsInvoiceUnitBilling Service', () => {
  let service: SettingsInvoiceUnitBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: ISettingsInvoiceUnitBilling;
  let expectedResult: ISettingsInvoiceUnitBilling | ISettingsInvoiceUnitBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SettingsInvoiceUnitBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      seller: 'AAAAAAA',
      personBuyerId: 'AAAAAAA',
      personRefererCopys: 'AAAAAAA',
      subscriptionId: 'AAAAAAA',
      useBillingAddress: false,
      paymentMethod: 'AAAAAAA',
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

    it('should create a SettingsInvoiceUnitBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SettingsInvoiceUnitBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SettingsInvoiceUnitBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          seller: 'BBBBBB',
          personBuyerId: 'BBBBBB',
          personRefererCopys: 'BBBBBB',
          subscriptionId: 'BBBBBB',
          useBillingAddress: true,
          paymentMethod: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SettingsInvoiceUnitBilling', () => {
      const patchObject = Object.assign(
        {
          personBuyerId: 'BBBBBB',
        },
        new SettingsInvoiceUnitBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SettingsInvoiceUnitBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          seller: 'BBBBBB',
          personBuyerId: 'BBBBBB',
          personRefererCopys: 'BBBBBB',
          subscriptionId: 'BBBBBB',
          useBillingAddress: true,
          paymentMethod: 'BBBBBB',
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

    it('should delete a SettingsInvoiceUnitBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSettingsInvoiceUnitBillingToCollectionIfMissing', () => {
      it('should add a SettingsInvoiceUnitBilling to an empty array', () => {
        const settingsInvoiceUnit: ISettingsInvoiceUnitBilling = { id: 'ABC' };
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing([], settingsInvoiceUnit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(settingsInvoiceUnit);
      });

      it('should not add a SettingsInvoiceUnitBilling to an array that contains it', () => {
        const settingsInvoiceUnit: ISettingsInvoiceUnitBilling = { id: 'ABC' };
        const settingsInvoiceUnitCollection: ISettingsInvoiceUnitBilling[] = [
          {
            ...settingsInvoiceUnit,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing(settingsInvoiceUnitCollection, settingsInvoiceUnit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SettingsInvoiceUnitBilling to an array that doesn't contain it", () => {
        const settingsInvoiceUnit: ISettingsInvoiceUnitBilling = { id: 'ABC' };
        const settingsInvoiceUnitCollection: ISettingsInvoiceUnitBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing(settingsInvoiceUnitCollection, settingsInvoiceUnit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(settingsInvoiceUnit);
      });

      it('should add only unique SettingsInvoiceUnitBilling to an array', () => {
        const settingsInvoiceUnitArray: ISettingsInvoiceUnitBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '8c6454ad-b9bd-46f8-b9f6-3786614c80fb' },
        ];
        const settingsInvoiceUnitCollection: ISettingsInvoiceUnitBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing(
          settingsInvoiceUnitCollection,
          ...settingsInvoiceUnitArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const settingsInvoiceUnit: ISettingsInvoiceUnitBilling = { id: 'ABC' };
        const settingsInvoiceUnit2: ISettingsInvoiceUnitBilling = { id: 'CBA' };
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing([], settingsInvoiceUnit, settingsInvoiceUnit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(settingsInvoiceUnit);
        expect(expectedResult).toContain(settingsInvoiceUnit2);
      });

      it('should accept null and undefined values', () => {
        const settingsInvoiceUnit: ISettingsInvoiceUnitBilling = { id: 'ABC' };
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing([], null, settingsInvoiceUnit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(settingsInvoiceUnit);
      });

      it('should return initial array if no SettingsInvoiceUnitBilling is added', () => {
        const settingsInvoiceUnitCollection: ISettingsInvoiceUnitBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSettingsInvoiceUnitBillingToCollectionIfMissing(settingsInvoiceUnitCollection, undefined, null);
        expect(expectedResult).toEqual(settingsInvoiceUnitCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
