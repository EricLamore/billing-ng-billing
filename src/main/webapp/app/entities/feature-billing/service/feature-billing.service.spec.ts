import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFeatureBilling, FeatureBilling } from '../feature-billing.model';

import { FeatureBillingService } from './feature-billing.service';

describe('FeatureBilling Service', () => {
  let service: FeatureBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IFeatureBilling;
  let expectedResult: IFeatureBilling | IFeatureBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FeatureBillingService);
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

    it('should create a FeatureBilling', () => {
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

      service.create(new FeatureBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FeatureBilling', () => {
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

    it('should partial update a FeatureBilling', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          lastUpdate: currentDate.format(DATE_FORMAT),
          description: 'BBBBBB',
          isVisible: true,
        },
        new FeatureBilling()
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

    it('should return a list of FeatureBilling', () => {
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

    it('should delete a FeatureBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addFeatureBillingToCollectionIfMissing', () => {
      it('should add a FeatureBilling to an empty array', () => {
        const feature: IFeatureBilling = { id: 'ABC' };
        expectedResult = service.addFeatureBillingToCollectionIfMissing([], feature);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feature);
      });

      it('should not add a FeatureBilling to an array that contains it', () => {
        const feature: IFeatureBilling = { id: 'ABC' };
        const featureCollection: IFeatureBilling[] = [
          {
            ...feature,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addFeatureBillingToCollectionIfMissing(featureCollection, feature);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FeatureBilling to an array that doesn't contain it", () => {
        const feature: IFeatureBilling = { id: 'ABC' };
        const featureCollection: IFeatureBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addFeatureBillingToCollectionIfMissing(featureCollection, feature);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feature);
      });

      it('should add only unique FeatureBilling to an array', () => {
        const featureArray: IFeatureBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '2d317a64-70ac-47ad-b340-eb51f58c3da7' }];
        const featureCollection: IFeatureBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addFeatureBillingToCollectionIfMissing(featureCollection, ...featureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const feature: IFeatureBilling = { id: 'ABC' };
        const feature2: IFeatureBilling = { id: 'CBA' };
        expectedResult = service.addFeatureBillingToCollectionIfMissing([], feature, feature2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(feature);
        expect(expectedResult).toContain(feature2);
      });

      it('should accept null and undefined values', () => {
        const feature: IFeatureBilling = { id: 'ABC' };
        expectedResult = service.addFeatureBillingToCollectionIfMissing([], null, feature, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(feature);
      });

      it('should return initial array if no FeatureBilling is added', () => {
        const featureCollection: IFeatureBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addFeatureBillingToCollectionIfMissing(featureCollection, undefined, null);
        expect(expectedResult).toEqual(featureCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
