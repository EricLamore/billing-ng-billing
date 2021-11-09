import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IOrganizationBilling, OrganizationBilling } from '../organization-billing.model';

import { OrganizationBillingService } from './organization-billing.service';

describe('OrganizationBilling Service', () => {
  let service: OrganizationBillingService;
  let httpMock: HttpTestingController;
  let elemDefault: IOrganizationBilling;
  let expectedResult: IOrganizationBilling | IOrganizationBilling[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganizationBillingService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      addr: 'AAAAAAA',
      city: 'AAAAAAA',
      country: 'AAAAAAA',
      name: 'AAAAAAA',
      registerDate: currentDate,
      status: 0,
      zipCode: 'AAAAAAA',
      individual: false,
      vatNumber: 'AAAAAAA',
      ipRanges: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          registerDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a OrganizationBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          registerDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          registerDate: currentDate,
        },
        returnedFromService
      );

      service.create(new OrganizationBilling()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrganizationBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          addr: 'BBBBBB',
          city: 'BBBBBB',
          country: 'BBBBBB',
          name: 'BBBBBB',
          registerDate: currentDate.format(DATE_FORMAT),
          status: 1,
          zipCode: 'BBBBBB',
          individual: true,
          vatNumber: 'BBBBBB',
          ipRanges: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          registerDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrganizationBilling', () => {
      const patchObject = Object.assign(
        {
          addr: 'BBBBBB',
          city: 'BBBBBB',
          name: 'BBBBBB',
          registerDate: currentDate.format(DATE_FORMAT),
          status: 1,
          zipCode: 'BBBBBB',
          individual: true,
        },
        new OrganizationBilling()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          registerDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrganizationBilling', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          addr: 'BBBBBB',
          city: 'BBBBBB',
          country: 'BBBBBB',
          name: 'BBBBBB',
          registerDate: currentDate.format(DATE_FORMAT),
          status: 1,
          zipCode: 'BBBBBB',
          individual: true,
          vatNumber: 'BBBBBB',
          ipRanges: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          registerDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a OrganizationBilling', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addOrganizationBillingToCollectionIfMissing', () => {
      it('should add a OrganizationBilling to an empty array', () => {
        const organization: IOrganizationBilling = { id: 'ABC' };
        expectedResult = service.addOrganizationBillingToCollectionIfMissing([], organization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organization);
      });

      it('should not add a OrganizationBilling to an array that contains it', () => {
        const organization: IOrganizationBilling = { id: 'ABC' };
        const organizationCollection: IOrganizationBilling[] = [
          {
            ...organization,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addOrganizationBillingToCollectionIfMissing(organizationCollection, organization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrganizationBilling to an array that doesn't contain it", () => {
        const organization: IOrganizationBilling = { id: 'ABC' };
        const organizationCollection: IOrganizationBilling[] = [{ id: 'CBA' }];
        expectedResult = service.addOrganizationBillingToCollectionIfMissing(organizationCollection, organization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organization);
      });

      it('should add only unique OrganizationBilling to an array', () => {
        const organizationArray: IOrganizationBilling[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'a0145aa6-301a-4dce-9dc0-5ac27038524b' }];
        const organizationCollection: IOrganizationBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addOrganizationBillingToCollectionIfMissing(organizationCollection, ...organizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organization: IOrganizationBilling = { id: 'ABC' };
        const organization2: IOrganizationBilling = { id: 'CBA' };
        expectedResult = service.addOrganizationBillingToCollectionIfMissing([], organization, organization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organization);
        expect(expectedResult).toContain(organization2);
      });

      it('should accept null and undefined values', () => {
        const organization: IOrganizationBilling = { id: 'ABC' };
        expectedResult = service.addOrganizationBillingToCollectionIfMissing([], null, organization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organization);
      });

      it('should return initial array if no OrganizationBilling is added', () => {
        const organizationCollection: IOrganizationBilling[] = [{ id: 'ABC' }];
        expectedResult = service.addOrganizationBillingToCollectionIfMissing(organizationCollection, undefined, null);
        expect(expectedResult).toEqual(organizationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
