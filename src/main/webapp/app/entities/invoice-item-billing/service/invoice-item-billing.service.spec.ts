import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ItemType } from 'app/entities/enumerations/item-type.model';
import { IInvoiceItemBilling, InvoiceItemBilling } from '../invoice-item-billing.model';

import { InvoiceItemBillingService } from './invoice-item-billing.service';

describe('InvoiceItemBilling Service', () => {
  let service: InvoiceItemBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IInvoiceItemBilling;
  let expectedResult: IInvoiceItemBilling | IInvoiceItemBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InvoiceItemBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      itemType: ItemType.SUBSCRIPTION,
      minStep: 0,
      maxStep: 0,
      quantity: 0,
      unitPrice: 0,
      discount: 0,
      price: 0,
      product: 'AAAAAAA',
      globalQuantity: 0,
      untilDate: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          untilDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a InvoiceItemBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          untilDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.create(new InvoiceItemBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InvoiceItemBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          itemType: 'BBBBBB',
          minStep: 1,
          maxStep: 1,
          quantity: 1,
          unitPrice: 1,
          discount: 1,
          price: 1,
          product: 'BBBBBB',
          globalQuantity: 1,
          untilDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InvoiceItemBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          itemType: 'BBBBBB',
          maxStep: 1,
          discount: 1,
          product: 'BBBBBB',
          globalQuantity: 1,
          untilDate: currentDate.format(DATE_FORMAT),
        },
        new InvoiceItemBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InvoiceItemBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          itemType: 'BBBBBB',
          minStep: 1,
          maxStep: 1,
          quantity: 1,
          unitPrice: 1,
          discount: 1,
          price: 1,
          product: 'BBBBBB',
          globalQuantity: 1,
          untilDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          untilDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a InvoiceItemBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInvoiceItemBillingToCollectionIfMissing', () => {
      it('should add a InvoiceItemBilling to an empty array', () => {
        const invoiceItem: IInvoiceItemBilling = { id: 'ABC' };
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing([], invoiceItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoiceItem);
      });

      it('should not add a InvoiceItemBilling to an array that contains it', () => {
        const invoiceItem: IInvoiceItemBilling = { id: 'ABC' };
        const invoiceItemCollection: IInvoiceItemBilling[] = [
          {
            ...invoiceItem,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing(invoiceItemCollection, invoiceItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InvoiceItemBilling to an array that doesn't contain it", () => {
        const invoiceItem: IInvoiceItemBilling = { id: 'ABC' };
        const invoiceItemCollection: IInvoiceItemBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing(invoiceItemCollection, invoiceItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoiceItem);
      });

      it('should add only unique InvoiceItemBilling to an array', () => {
        const invoiceItemArray: IInvoiceItemBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '60a487e6-31ab-4e1f-9327-12e061687079' }];
        const invoiceItemCollection: IInvoiceItemBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing(invoiceItemCollection, ...invoiceItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const invoiceItem: IInvoiceItemBilling = { id: 'ABC' };
        const invoiceItem2: IInvoiceItemBilling = { id: 'CBA' };
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing([], invoiceItem, invoiceItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoiceItem);
        expect(expectedResult).toContain(invoiceItem2);
      });

      it('should accept null and undefined values', () => {
        const invoiceItem: IInvoiceItemBilling = { id: 'ABC' };
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing([], null, invoiceItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoiceItem);
      });

      it('should return initial array if no InvoiceItemBilling is added', () => {
        const invoiceItemCollection: IInvoiceItemBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addInvoiceItemBillingToCollectionIfMissing(invoiceItemCollection, undefined, null);
        expect(expectedResult).toEqual(invoiceItemCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
