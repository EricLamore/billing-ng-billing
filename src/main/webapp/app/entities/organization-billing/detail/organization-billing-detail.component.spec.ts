import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrganizationBillingDetailComponent } from './organization-billing-detail.component';

describe('OrganizationBilling Management Detail Component', () => {
  let comp: OrganizationBillingDetailComponent;
  let fixture: ComponentFixture<OrganizationBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrganizationBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ organization: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(OrganizationBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrganizationBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load organization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.organization).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
