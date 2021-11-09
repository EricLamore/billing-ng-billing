import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISubscriptionFeatureBilling, SubscriptionFeatureBilling } from '../subscription-feature-billing.model';

import { SubscriptionFeatureBillingService } from './subscription-feature-billing.service';

describe('SubscriptionFeatureBilling Service', () => {
  let service: SubscriptionFeatureBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: ISubscriptionFeatureBilling;
  let expectedResult: ISubscriptionFeatureBilling | ISubscriptionFeatureBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubscriptionFeatureBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      name: 'AAAAAAA',
      lastUpdate: currentDate,
      description: 'AAAAAAA',
      isVisible: false,
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

    it('should create a SubscriptionFeatureBilling', () => {
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

      service.create(new SubscriptionFeatureBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubscriptionFeatureBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          isVisible: true,
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

    it('should partial update a SubscriptionFeatureBilling', () => {
      const patchObject = Object.assign(
        {
          description: 'BBBBBB',
          isVisible: true,
        },
        new SubscriptionFeatureBilling()
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

    it('should return a list of SubscriptionFeatureBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          isVisible: true,
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

    it('should delete a SubscriptionFeatureBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSubscriptionFeatureBillingToCollectionIfMissing', () => {
      it('should add a SubscriptionFeatureBilling to an empty array', () => {
        const subscriptionFeature: ISubscriptionFeatureBilling = { id: 'ABC' };
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing([], subscriptionFeature);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subscriptionFeature);
      });

      it('should not add a SubscriptionFeatureBilling to an array that contains it', () => {
        const subscriptionFeature: ISubscriptionFeatureBilling = { id: 'ABC' };
        const subscriptionFeatureCollection: ISubscriptionFeatureBilling[] = [
          {
            ...subscriptionFeature,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing(subscriptionFeatureCollection, subscriptionFeature);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubscriptionFeatureBilling to an array that doesn't contain it", () => {
        const subscriptionFeature: ISubscriptionFeatureBilling = { id: 'ABC' };
        const subscriptionFeatureCollection: ISubscriptionFeatureBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing(subscriptionFeatureCollection, subscriptionFeature);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subscriptionFeature);
      });

      it('should add only unique SubscriptionFeatureBilling to an array', () => {
        const subscriptionFeatureArray: ISubscriptionFeatureBilling[] = [
          { id: 'ABC' },
          { id: 'CBA' },
          { id: 'c57bcb45-2f23-4fd9-ae3c-a0d15aba9a64' },
        ];
        const subscriptionFeatureCollection: ISubscriptionFeatureBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing(
          subscriptionFeatureCollection,
          ...subscriptionFeatureArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subscriptionFeature: ISubscriptionFeatureBilling = { id: 'ABC' };
        const subscriptionFeature2: ISubscriptionFeatureBilling = { id: 'CBA' };
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing([], subscriptionFeature, subscriptionFeature2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subscriptionFeature);
        expect(expectedResult).toContain(subscriptionFeature2);
      });

      it('should accept null and undefined values', () => {
        const subscriptionFeature: ISubscriptionFeatureBilling = { id: 'ABC' };
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing([], null, subscriptionFeature, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subscriptionFeature);
      });

      it('should return initial array if no SubscriptionFeatureBilling is added', () => {
        const subscriptionFeatureCollection: ISubscriptionFeatureBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addSubscriptionFeatureBillingToCollectionIfMissing(subscriptionFeatureCollection, undefined, null);
        expect(expectedResult).toEqual(subscriptionFeatureCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
