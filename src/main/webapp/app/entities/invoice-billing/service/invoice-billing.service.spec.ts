import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { InvoiceType } from 'app/entities/enumerations/invoice-type.model';
import { Status } from 'app/entities/enumerations/status.model';
import { IInvoiceBilling, InvoiceBilling } from '../invoice-billing.model';

import { InvoiceBillingService } from './invoice-billing.service';

describe('InvoiceBilling Service', () => {
  let service: InvoiceBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IInvoiceBilling;
  let expectedResult: IInvoiceBilling | IInvoiceBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InvoiceBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      humanId: 'AAAAAAA',
      type: InvoiceType.INVOICE,
      customerId: 'AAAAAAA',
      customerName: 'AAAAAAA',
      month: 0,
      year: 0,
      emissionDate: currentDate,
      items: 'AAAAAAA',
      vat: 0,
      dueDate: currentDate,
      dateToSend: currentDate,
      sendDate: currentDate,
      dunningSendDate: currentDate,
      creditNoteSendDate: currentDate,
      status: Status.DRAFT,
      comments: 'AAAAAAA',
      pvalidationDate: currentDate,
      validatorId: 'AAAAAAA',
      payment: 'AAAAAAA',
      paymentsHistoric: 'AAAAAAA',
      paymentMethod: 'AAAAAAA',
      refund: 'AAAAAAA',
      purchaseOrder: 'AAAAAAA',
      message: 'AAAAAAA',
      additionalItems: 'AAAAAAA',
      totalPriceGross: 0,
      refundAmount: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          emissionDate: currentDate.format(DATE_FORMAT),
          dueDate: currentDate.format(DATE_FORMAT),
          dateToSend: currentDate.format(DATE_FORMAT),
          sendDate: currentDate.format(DATE_FORMAT),
          dunningSendDate: currentDate.format(DATE_FORMAT),
          creditNoteSendDate: currentDate.format(DATE_FORMAT),
          pvalidationDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a InvoiceBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          emissionDate: currentDate.format(DATE_FORMAT),
          dueDate: currentDate.format(DATE_FORMAT),
          dateToSend: currentDate.format(DATE_FORMAT),
          sendDate: currentDate.format(DATE_FORMAT),
          dunningSendDate: currentDate.format(DATE_FORMAT),
          creditNoteSendDate: currentDate.format(DATE_FORMAT),
          pvalidationDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          emissionDate: currentDate,
          dueDate: currentDate,
          dateToSend: currentDate,
          sendDate: currentDate,
          dunningSendDate: currentDate,
          creditNoteSendDate: currentDate,
          pvalidationDate: currentDate,
        },
        returnedFromService
      );

      service.create(new InvoiceBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InvoiceBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          humanId: 'BBBBBB',
          type: 'BBBBBB',
          customerId: 'BBBBBB',
          customerName: 'BBBBBB',
          month: 1,
          year: 1,
          emissionDate: currentDate.format(DATE_FORMAT),
          items: 'BBBBBB',
          vat: 1,
          dueDate: currentDate.format(DATE_FORMAT),
          dateToSend: currentDate.format(DATE_FORMAT),
          sendDate: currentDate.format(DATE_FORMAT),
          dunningSendDate: currentDate.format(DATE_FORMAT),
          creditNoteSendDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          comments: 'BBBBBB',
          pvalidationDate: currentDate.format(DATE_FORMAT),
          validatorId: 'BBBBBB',
          payment: 'BBBBBB',
          paymentsHistoric: 'BBBBBB',
          paymentMethod: 'BBBBBB',
          refund: 'BBBBBB',
          purchaseOrder: 'BBBBBB',
          message: 'BBBBBB',
          additionalItems: 'BBBBBB',
          totalPriceGross: 1,
          refundAmount: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          emissionDate: currentDate,
          dueDate: currentDate,
          dateToSend: currentDate,
          sendDate: currentDate,
          dunningSendDate: currentDate,
          creditNoteSendDate: currentDate,
          pvalidationDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InvoiceBilling', () => {
      const patchObject = Object.assign(
        {
          type: 'BBBBBB',
          customerId: 'BBBBBB',
          customerName: 'BBBBBB',
          year: 1,
          items: 'BBBBBB',
          dueDate: currentDate.format(DATE_FORMAT),
          sendDate: currentDate.format(DATE_FORMAT),
          comments: 'BBBBBB',
          validatorId: 'BBBBBB',
          paymentsHistoric: 'BBBBBB',
          paymentMethod: 'BBBBBB',
          purchaseOrder: 'BBBBBB',
        },
        new InvoiceBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          emissionDate: currentDate,
          dueDate: currentDate,
          dateToSend: currentDate,
          sendDate: currentDate,
          dunningSendDate: currentDate,
          creditNoteSendDate: currentDate,
          pvalidationDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InvoiceBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          humanId: 'BBBBBB',
          type: 'BBBBBB',
          customerId: 'BBBBBB',
          customerName: 'BBBBBB',
          month: 1,
          year: 1,
          emissionDate: currentDate.format(DATE_FORMAT),
          items: 'BBBBBB',
          vat: 1,
          dueDate: currentDate.format(DATE_FORMAT),
          dateToSend: currentDate.format(DATE_FORMAT),
          sendDate: currentDate.format(DATE_FORMAT),
          dunningSendDate: currentDate.format(DATE_FORMAT),
          creditNoteSendDate: currentDate.format(DATE_FORMAT),
          status: 'BBBBBB',
          comments: 'BBBBBB',
          pvalidationDate: currentDate.format(DATE_FORMAT),
          validatorId: 'BBBBBB',
          payment: 'BBBBBB',
          paymentsHistoric: 'BBBBBB',
          paymentMethod: 'BBBBBB',
          refund: 'BBBBBB',
          purchaseOrder: 'BBBBBB',
          message: 'BBBBBB',
          additionalItems: 'BBBBBB',
          totalPriceGross: 1,
          refundAmount: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          emissionDate: currentDate,
          dueDate: currentDate,
          dateToSend: currentDate,
          sendDate: currentDate,
          dunningSendDate: currentDate,
          creditNoteSendDate: currentDate,
          pvalidationDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a InvoiceBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addInvoiceBillingToCollectionIfMissing', () => {
      it('should add a InvoiceBilling to an empty array', () => {
        const invoice: IInvoiceBilling = { id: 'ABC' };
        expectedResult = service.addInvoiceBillingToCollectionIfMissing([], invoice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoice);
      });

      it('should not add a InvoiceBilling to an array that contains it', () => {
        const invoice: IInvoiceBilling = { id: 'ABC' };
        const invoiceCollection: IInvoiceBilling[] = [
          {
            ...invoice,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addInvoiceBillingToCollectionIfMissing(invoiceCollection, invoice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InvoiceBilling to an array that doesn't contain it", () => {
        const invoice: IInvoiceBilling = { id: 'ABC' };
        const invoiceCollection: IInvoiceBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addInvoiceBillingToCollectionIfMissing(invoiceCollection, invoice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoice);
      });

      it('should add only unique InvoiceBilling to an array', () => {
        const invoiceArray: IInvoiceBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '234a9e44-31b0-4558-8055-777991228afb' }];
        const invoiceCollection: IInvoiceBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addInvoiceBillingToCollectionIfMissing(invoiceCollection, ...invoiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const invoice: IInvoiceBilling = { id: 'ABC' };
        const invoice2: IInvoiceBilling = { id: 'CBA' };
        expectedResult = service.addInvoiceBillingToCollectionIfMissing([], invoice, invoice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(invoice);
        expect(expectedResult).toContain(invoice2);
      });

      it('should accept null and undefined values', () => {
        const invoice: IInvoiceBilling = { id: 'ABC' };
        expectedResult = service.addInvoiceBillingToCollectionIfMissing([], null, invoice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(invoice);
      });

      it('should return initial array if no InvoiceBilling is added', () => {
        const invoiceCollection: IInvoiceBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addInvoiceBillingToCollectionIfMissing(invoiceCollection, undefined, null);
        expect(expectedResult).toEqual(invoiceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
