import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaymentMethodBilling, PaymentMethodBilling } from '../payment-method-billing.model';

import { PaymentMethodBillingService } from './payment-method-billing.service';

describe('PaymentMethodBilling Service', () => {
  let service: PaymentMethodBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaymentMethodBilling;
  let expectedResult: IPaymentMethodBilling | IPaymentMethodBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentMethodBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      typeOfMean: 'AAAAAAA',
      accountId: 'AAAAAAA',
      iban: 'AAAAAAA',
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

    it('should create a PaymentMethodBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PaymentMethodBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentMethodBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          typeOfMean: 'BBBBBB',
          accountId: 'BBBBBB',
          iban: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentMethodBilling', () => {
      const patchObject = Object.assign(
        {
          accountId: 'BBBBBB',
          iban: 'BBBBBB',
        },
        new PaymentMethodBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentMethodBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          typeOfMean: 'BBBBBB',
          accountId: 'BBBBBB',
          iban: 'BBBBBB',
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

    it('should delete a PaymentMethodBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaymentMethodBillingToCollectionIfMissing', () => {
      it('should add a PaymentMethodBilling to an empty array', () => {
        const paymentMethod: IPaymentMethodBilling = { id: 'ABC' };
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing([], paymentMethod);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentMethod);
      });

      it('should not add a PaymentMethodBilling to an array that contains it', () => {
        const paymentMethod: IPaymentMethodBilling = { id: 'ABC' };
        const paymentMethodCollection: IPaymentMethodBilling[] = [
          {
            ...paymentMethod,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing(paymentMethodCollection, paymentMethod);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentMethodBilling to an array that doesn't contain it", () => {
        const paymentMethod: IPaymentMethodBilling = { id: 'ABC' };
        const paymentMethodCollection: IPaymentMethodBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing(paymentMethodCollection, paymentMethod);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentMethod);
      });

      it('should add only unique PaymentMethodBilling to an array', () => {
        const paymentMethodArray: IPaymentMethodBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'da37bbe7-2a86-4ccc-84e6-cc8f46066d69' }];
        const paymentMethodCollection: IPaymentMethodBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing(paymentMethodCollection, ...paymentMethodArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentMethod: IPaymentMethodBilling = { id: 'ABC' };
        const paymentMethod2: IPaymentMethodBilling = { id: 'CBA' };
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing([], paymentMethod, paymentMethod2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentMethod);
        expect(expectedResult).toContain(paymentMethod2);
      });

      it('should accept null and undefined values', () => {
        const paymentMethod: IPaymentMethodBilling = { id: 'ABC' };
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing([], null, paymentMethod, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentMethod);
      });

      it('should return initial array if no PaymentMethodBilling is added', () => {
        const paymentMethodCollection: IPaymentMethodBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addPaymentMethodBillingToCollectionIfMissing(paymentMethodCollection, undefined, null);
        expect(expectedResult).toEqual(paymentMethodCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
