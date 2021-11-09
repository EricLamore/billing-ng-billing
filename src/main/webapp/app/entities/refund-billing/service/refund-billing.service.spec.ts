import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRefundBilling, RefundBilling } from '../refund-billing.model';

import { RefundBillingService } from './refund-billing.service';

describe('RefundBilling Service', () => {
  let service: RefundBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IRefundBilling;
  let expectedResult: IRefundBilling | IRefundBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RefundBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      reference: 'AAAAAAA',
      amount: 0,
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

    it('should create a RefundBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new RefundBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RefundBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          reference: 'BBBBBB',
          amount: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RefundBilling', () => {
      const patchObject = Object.assign({}, new RefundBilling());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RefundBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          reference: 'BBBBBB',
          amount: 1,
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

    it('should delete a RefundBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addRefundBillingToCollectionIfMissing', () => {
      it('should add a RefundBilling to an empty array', () => {
        const refund: IRefundBilling = { id: 'ABC' };
        expectedResult = service.addRefundBillingToCollectionIfMissing([], refund);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refund);
      });

      it('should not add a RefundBilling to an array that contains it', () => {
        const refund: IRefundBilling = { id: 'ABC' };
        const refundCollection: IRefundBilling[] = [
          {
            ...refund,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addRefundBillingToCollectionIfMissing(refundCollection, refund);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RefundBilling to an array that doesn't contain it", () => {
        const refund: IRefundBilling = { id: 'ABC' };
        const refundCollection: IRefundBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addRefundBillingToCollectionIfMissing(refundCollection, refund);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refund);
      });

      it('should add only unique RefundBilling to an array', () => {
        const refundArray: IRefundBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'd3dfb8e8-3d46-4d3e-8716-13c60e1be935' }];
        const refundCollection: IRefundBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addRefundBillingToCollectionIfMissing(refundCollection, ...refundArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const refund: IRefundBilling = { id: 'ABC' };
        const refund2: IRefundBilling = { id: 'CBA' };
        expectedResult = service.addRefundBillingToCollectionIfMissing([], refund, refund2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refund);
        expect(expectedResult).toContain(refund2);
      });

      it('should accept null and undefined values', () => {
        const refund: IRefundBilling = { id: 'ABC' };
        expectedResult = service.addRefundBillingToCollectionIfMissing([], null, refund, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refund);
      });

      it('should return initial array if no RefundBilling is added', () => {
        const refundCollection: IRefundBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addRefundBillingToCollectionIfMissing(refundCollection, undefined, null);
        expect(expectedResult).toEqual(refundCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
