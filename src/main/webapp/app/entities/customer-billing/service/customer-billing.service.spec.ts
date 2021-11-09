import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICustomerBilling, CustomerBilling } from '../customer-billing.model';

import { CustomerBillingService } from './customer-billing.service';

describe('CustomerBilling Service', () => {
  let service: CustomerBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: ICustomerBilling;
  let expectedResult: ICustomerBilling | ICustomerBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CustomerBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      lastUpdate: currentDate,
      description: 'AAAAAAA',
      taxNo: 'AAAAAAA',
      thirdPartyAccountingCode: 'AAAAAAA',
      siret: 'AAAAAAA',
      ownerId: 'AAAAAAA',
      isParticular: false,
      personReferers: 'AAAAAAA',
      meansPayments: 'AAAAAAA',
      subscriptions: 'AAAAAAA',
      usages: 'AAAAAAA',
      settingsInvoice: 'AAAAAAA',
      partner: false,
      partnerId: 'AAAAAAA',
      customerId: 'AAAAAAA',
      customerName: 'AAAAAAA',
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

    it('should create a CustomerBilling', () => {
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

      service.create(new CustomerBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CustomerBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          taxNo: 'BBBBBB',
          thirdPartyAccountingCode: 'BBBBBB',
          siret: 'BBBBBB',
          ownerId: 'BBBBBB',
          isParticular: true,
          personReferers: 'BBBBBB',
          meansPayments: 'BBBBBB',
          subscriptions: 'BBBBBB',
          usages: 'BBBBBB',
          settingsInvoice: 'BBBBBB',
          partner: true,
          partnerId: 'BBBBBB',
          customerId: 'BBBBBB',
          customerName: 'BBBBBB',
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

    it('should partial update a CustomerBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          description: 'BBBBBB',
          thirdPartyAccountingCode: 'BBBBBB',
          siret: 'BBBBBB',
          personReferers: 'BBBBBB',
          settingsInvoice: 'BBBBBB',
          partnerId: 'BBBBBB',
        },
        new CustomerBilling()
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

    it('should return a list of CustomerBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          taxNo: 'BBBBBB',
          thirdPartyAccountingCode: 'BBBBBB',
          siret: 'BBBBBB',
          ownerId: 'BBBBBB',
          isParticular: true,
          personReferers: 'BBBBBB',
          meansPayments: 'BBBBBB',
          subscriptions: 'BBBBBB',
          usages: 'BBBBBB',
          settingsInvoice: 'BBBBBB',
          partner: true,
          partnerId: 'BBBBBB',
          customerId: 'BBBBBB',
          customerName: 'BBBBBB',
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

    it('should delete a CustomerBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCustomerBillingToCollectionIfMissing', () => {
      it('should add a CustomerBilling to an empty array', () => {
        const customer: ICustomerBilling = { id: 'ABC' };
        expectedResult = service.addCustomerBillingToCollectionIfMissing([], customer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(customer);
      });

      it('should not add a CustomerBilling to an array that contains it', () => {
        const customer: ICustomerBilling = { id: 'ABC' };
        const customerCollection: ICustomerBilling[] = [
          {
            ...customer,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addCustomerBillingToCollectionIfMissing(customerCollection, customer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CustomerBilling to an array that doesn't contain it", () => {
        const customer: ICustomerBilling = { id: 'ABC' };
        const customerCollection: ICustomerBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addCustomerBillingToCollectionIfMissing(customerCollection, customer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(customer);
      });

      it('should add only unique CustomerBilling to an array', () => {
        const customerArray: ICustomerBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '8043e099-2ac3-41e5-bc36-c55fe047233d' }];
        const customerCollection: ICustomerBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addCustomerBillingToCollectionIfMissing(customerCollection, ...customerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const customer: ICustomerBilling = { id: 'ABC' };
        const customer2: ICustomerBilling = { id: 'CBA' };
        expectedResult = service.addCustomerBillingToCollectionIfMissing([], customer, customer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(customer);
        expect(expectedResult).toContain(customer2);
      });

      it('should accept null and undefined values', () => {
        const customer: ICustomerBilling = { id: 'ABC' };
        expectedResult = service.addCustomerBillingToCollectionIfMissing([], null, customer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(customer);
      });

      it('should return initial array if no CustomerBilling is added', () => {
        const customerCollection: ICustomerBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addCustomerBillingToCollectionIfMissing(customerCollection, undefined, null);
        expect(expectedResult).toEqual(customerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
