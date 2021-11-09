import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonRefererBilling, PersonRefererBilling } from '../person-referer-billing.model';

import { PersonRefererBillingService } from './person-referer-billing.service';

describe('PersonRefererBilling Service', () => {
  let service: PersonRefererBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IPersonRefererBilling;
  let expectedResult: IPersonRefererBilling | IPersonRefererBilling[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonRefererBillingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 'AAAAAAA',
      firstname: 'AAAAAAA',
      lastname: 'AAAAAAA',
      email: 'AAAAAAA',
      job: 'AAAAAAA',
      phoneNumber: 'AAAAAAA',
      mobile: 'AAAAAAA',
      fax: 'AAAAAAA',
      description: 'AAAAAAA',
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

    it('should create a PersonRefererBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PersonRefererBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonRefererBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          firstname: 'BBBBBB',
          lastname: 'BBBBBB',
          email: 'BBBBBB',
          job: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          mobile: 'BBBBBB',
          fax: 'BBBBBB',
          description: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonRefererBilling', () => {
      const patchObject = Object.assign(
        {
          email: 'BBBBBB',
          job: 'BBBBBB',
          phoneNumber: 'BBBBBB',
        },
        new PersonRefererBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonRefererBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          firstname: 'BBBBBB',
          lastname: 'BBBBBB',
          email: 'BBBBBB',
          job: 'BBBBBB',
          phoneNumber: 'BBBBBB',
          mobile: 'BBBBBB',
          fax: 'BBBBBB',
          description: 'BBBBBB',
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

    it('should delete a PersonRefererBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPersonRefererBillingToCollectionIfMissing', () => {
      it('should add a PersonRefererBilling to an empty array', () => {
        const personReferer: IPersonRefererBilling = { id: 'ABC' };
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing([], personReferer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personReferer);
      });

      it('should not add a PersonRefererBilling to an array that contains it', () => {
        const personReferer: IPersonRefererBilling = { id: 'ABC' };
        const personRefererCollection: IPersonRefererBilling[] = [
          {
            ...personReferer,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing(personRefererCollection, personReferer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonRefererBilling to an array that doesn't contain it", () => {
        const personReferer: IPersonRefererBilling = { id: 'ABC' };
        const personRefererCollection: IPersonRefererBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing(personRefererCollection, personReferer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personReferer);
      });

      it('should add only unique PersonRefererBilling to an array', () => {
        const personRefererArray: IPersonRefererBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: '86129dff-2310-482c-8dd4-0e9e907f3ba2' }];
        const personRefererCollection: IPersonRefererBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing(personRefererCollection, ...personRefererArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personReferer: IPersonRefererBilling = { id: 'ABC' };
        const personReferer2: IPersonRefererBilling = { id: 'CBA' };
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing([], personReferer, personReferer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personReferer);
        expect(expectedResult).toContain(personReferer2);
      });

      it('should accept null and undefined values', () => {
        const personReferer: IPersonRefererBilling = { id: 'ABC' };
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing([], null, personReferer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personReferer);
      });

      it('should return initial array if no PersonRefererBilling is added', () => {
        const personRefererCollection: IPersonRefererBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addPersonRefererBillingToCollectionIfMissing(personRefererCollection, undefined, null);
        expect(expectedResult).toEqual(personRefererCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
