import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISubscriptionTmpBilling, SubscriptionTmpBilling } from '../subscription-tmp-billing.model';

import { SubscriptionTmpBillingService } from './subscription-tmp-billing.service';

describe('SubscriptionTmpBilling Service', () => {
  let service: SubscriptionTmpBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubscriptionTmpBilling;
  let expectedResult: ISubscriptionTmpBilling | ISubscriptionTmpBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubscriptionTmpBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      lastUpdate: currentDate,
      description: 'AAAAAAA',
      commercialName: 'AAAAAAA',
      ratePlans: 'AAAAAAA',
      subscriptionFeatures: 'AAAAAAA',
      version: 'AAAAAAA',
      usages: 'AAAAAAA',
      freeMonths: 0,
      invoiceItemDateds: 'AAAAAAA',
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

    it('should create a SubscriptionTmpBilling', () => {
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

      service.create(new SubscriptionTmpBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubscriptionTmpBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          commercialName: 'BBBBBB',
          ratePlans: 'BBBBBB',
          subscriptionFeatures: 'BBBBBB',
          version: 'BBBBBB',
          usages: 'BBBBBB',
          freeMonths: 1,
          invoiceItemDateds: 'BBBBBB',
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

    it('should partial update a SubscriptionTmpBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          ratePlans: 'BBBBBB',
          subscriptionFeatures: 'BBBBBB',
          version: 'BBBBBB',
          usages: 'BBBBBB',
          freeMonths: 1,
        },
        new SubscriptionTmpBilling()
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

    it('should return a list of SubscriptionTmpBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          commercialName: 'BBBBBB',
          ratePlans: 'BBBBBB',
          subscriptionFeatures: 'BBBBBB',
          version: 'BBBBBB',
          usages: 'BBBBBB',
          freeMonths: 1,
          invoiceItemDateds: 'BBBBBB',
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

    it('should delete a SubscriptionTmpBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubscriptionTmpBillingToCollectionIfMissing', () => {
      it('should add a SubscriptionTmpBilling to an empty array', () => {
        const subscriptionTmp: ISubscriptionTmpBilling = { id: 'ABC' };
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing([], subscriptionTmp);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subscriptionTmp);
      });

      it('should not add a SubscriptionTmpBilling to an array that contains it', () => {
        const subscriptionTmp: ISubscriptionTmpBilling = { id: 'ABC' };
        const subscriptionTmpCollection: ISubscriptionTmpBilling[] = [
          {
            ...subscriptionTmp,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing(subscriptionTmpCollection, subscriptionTmp);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubscriptionTmpBilling to an array that doesn't contain it", () => {
        const subscriptionTmp: ISubscriptionTmpBilling = { id: 'ABC' };
        const subscriptionTmpCollection: ISubscriptionTmpBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing(subscriptionTmpCollection, subscriptionTmp);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subscriptionTmp);
      });

      it('should add only unique SubscriptionTmpBilling to an array', () => {
        const subscriptionTmpArray: ISubscriptionTmpBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: '81a82045-7bb5-4256-8792-9373fbdfa143' },
        ];
        const subscriptionTmpCollection: ISubscriptionTmpBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing(subscriptionTmpCollection, ...subscriptionTmpArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subscriptionTmp: ISubscriptionTmpBilling = { id: 'ABC' };
        const subscriptionTmp2: ISubscriptionTmpBilling = { id: 'CBA' };
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing([], subscriptionTmp, subscriptionTmp2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subscriptionTmp);
        expect(expectedResult).toContain(subscriptionTmp2);
      });

      it('should accept null and undefined values', () => {
        const subscriptionTmp: ISubscriptionTmpBilling = { id: 'ABC' };
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing([], null, subscriptionTmp, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subscriptionTmp);
      });

      it('should return initial array if no SubscriptionTmpBilling is added', () => {
        const subscriptionTmpCollection: ISubscriptionTmpBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSubscriptionTmpBillingToCollectionIfMissing(subscriptionTmpCollection, undefined, null);
        expect(expectedResult).toEqual(subscriptionTmpCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
