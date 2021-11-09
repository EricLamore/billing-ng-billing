import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Period } from 'app/entities/enumerations/period.model';
import { ISettingsInvoiceBilling, SettingsInvoiceBilling } from '../settings-invoice-billing.model';

import { SettingsInvoiceBillingService } from './settings-invoice-billing.service';

describe('SettingsInvoiceBilling Service', () => {
  let service: SettingsInvoiceBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: ISettingsInvoiceBilling;
  let expectedResult: ISettingsInvoiceBilling | ISettingsInvoiceBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SettingsInvoiceBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      global: 'AAAAAAA',
      subscription: 'AAAAAAA',
      taxPerCent: 0,
      detailSkipped: false,
      manualBillingOnly: false,
      billingActive: false,
      perOrganization: false,
      fullyAutomatic: false,
      period: Period.MONTHLY,
      locale: 'AAAAAAA',
      paymentTerms: 'AAAAAAA',
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

    it('should create a SettingsInvoiceBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SettingsInvoiceBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SettingsInvoiceBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          global: 'BBBBBB',
          subscription: 'BBBBBB',
          taxPerCent: 1,
          detailSkipped: true,
          manualBillingOnly: true,
          billingActive: true,
          perOrganization: true,
          fullyAutomatic: true,
          period: 'BBBBBB',
          locale: 'BBBBBB',
          paymentTerms: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SettingsInvoiceBilling', () => {
      const patchObject = Object.assign(
        {
          global: 'BBBBBB',
          manualBillingOnly: true,
          perOrganization: true,
          paymentTerms: 'BBBBBB',
        },
        new SettingsInvoiceBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SettingsInvoiceBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          global: 'BBBBBB',
          subscription: 'BBBBBB',
          taxPerCent: 1,
          detailSkipped: true,
          manualBillingOnly: true,
          billingActive: true,
          perOrganization: true,
          fullyAutomatic: true,
          period: 'BBBBBB',
          locale: 'BBBBBB',
          paymentTerms: 'BBBBBB',
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

    it('should delete a SettingsInvoiceBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSettingsInvoiceBillingToCollectionIfMissing', () => {
      it('should add a SettingsInvoiceBilling to an empty array', () => {
        const settingsInvoice: ISettingsInvoiceBilling = { id: 'ABC' };
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing([], settingsInvoice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(settingsInvoice);
      });

      it('should not add a SettingsInvoiceBilling to an array that contains it', () => {
        const settingsInvoice: ISettingsInvoiceBilling = { id: 'ABC' };
        const settingsInvoiceCollection: ISettingsInvoiceBilling[] = [
          {
            ...settingsInvoice,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing(settingsInvoiceCollection, settingsInvoice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SettingsInvoiceBilling to an array that doesn't contain it", () => {
        const settingsInvoice: ISettingsInvoiceBilling = { id: 'ABC' };
        const settingsInvoiceCollection: ISettingsInvoiceBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing(settingsInvoiceCollection, settingsInvoice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(settingsInvoice);
      });

      it('should add only unique SettingsInvoiceBilling to an array', () => {
        const settingsInvoiceArray: ISettingsInvoiceBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '0777d3ad-04be-4962-80c9-2db3370295c7' },
        ];
        const settingsInvoiceCollection: ISettingsInvoiceBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing(settingsInvoiceCollection, ...settingsInvoiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const settingsInvoice: ISettingsInvoiceBilling = { id: 'ABC' };
        const settingsInvoice2: ISettingsInvoiceBilling = { id: 'CBA' };
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing([], settingsInvoice, settingsInvoice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(settingsInvoice);
        expect(expectedResult).toContain(settingsInvoice2);
      });

      it('should accept null and undefined values', () => {
        const settingsInvoice: ISettingsInvoiceBilling = { id: 'ABC' };
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing([], null, settingsInvoice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(settingsInvoice);
      });

      it('should return initial array if no SettingsInvoiceBilling is added', () => {
        const settingsInvoiceCollection: ISettingsInvoiceBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSettingsInvoiceBillingToCollectionIfMissing(settingsInvoiceCollection, undefined, null);
        expect(expectedResult).toEqual(settingsInvoiceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
