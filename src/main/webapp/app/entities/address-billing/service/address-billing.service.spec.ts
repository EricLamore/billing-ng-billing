import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAddressBilling, AddressBilling } from '../address-billing.model';

import { AddressBillingService } from './address-billing.service';

describe('AddressBilling Service', () => {
  let service: AddressBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IAddressBilling;
  let expectedResult: IAddressBilling | IAddressBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AddressBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      recipient: 'AAAAAAA',
      postOfficeBox: 'AAAAAAA',
      complement: 'AAAAAAA',
      street: 'AAAAAAA',
      postcode: 'AAAAAAA',
      city: 'AAAAAAA',
      country: 'AAAAAAA',
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

    it('should create a AddressBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AddressBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AddressBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          recipient: 'BBBBBB',
          postOfficeBox: 'BBBBBB',
          complement: 'BBBBBB',
          street: 'BBBBBB',
          postcode: 'BBBBBB',
          city: 'BBBBBB',
          country: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AddressBilling', () => {
      const patchObject = Object.assign(
        {
          city: 'BBBBBB',
          country: 'BBBBBB',
        },
        new AddressBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AddressBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          recipient: 'BBBBBB',
          postOfficeBox: 'BBBBBB',
          complement: 'BBBBBB',
          street: 'BBBBBB',
          postcode: 'BBBBBB',
          city: 'BBBBBB',
          country: 'BBBBBB',
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

    it('should delete a AddressBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAddressBillingToCollectionIfMissing', () => {
      it('should add a AddressBilling to an empty array', () => {
        const address: IAddressBilling = { id: 'ABC' };
        expectedResult = service.addAddressBillingToCollectionIfMissing([], address);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(address);
      });

      it('should not add a AddressBilling to an array that contains it', () => {
        const address: IAddressBilling = { id: 'ABC' };
        const addressCollection: IAddressBilling[] = [
          {
            ...address,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addAddressBillingToCollectionIfMissing(addressCollection, address);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AddressBilling to an array that doesn't contain it", () => {
        const address: IAddressBilling = { id: 'ABC' };
        const addressCollection: IAddressBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addAddressBillingToCollectionIfMissing(addressCollection, address);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(address);
      });

      it('should add only unique AddressBilling to an array', () => {
        const addressArray: IAddressBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '90aa773c-dd2e-49c6-92be-dcaedb2201b9' }];
        const addressCollection: IAddressBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addAddressBillingToCollectionIfMissing(addressCollection, ...addressArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const address: IAddressBilling = { id: 'ABC' };
        const address2: IAddressBilling = { id: 'CBA' };
        expectedResult = service.addAddressBillingToCollectionIfMissing([], address, address2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(address);
        expect(expectedResult).toContain(address2);
      });

      it('should accept null and undefined values', () => {
        const address: IAddressBilling = { id: 'ABC' };
        expectedResult = service.addAddressBillingToCollectionIfMissing([], null, address, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(address);
      });

      it('should return initial array if no AddressBilling is added', () => {
        const addressCollection: IAddressBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addAddressBillingToCollectionIfMissing(addressCollection, undefined, null);
        expect(expectedResult).toEqual(addressCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
