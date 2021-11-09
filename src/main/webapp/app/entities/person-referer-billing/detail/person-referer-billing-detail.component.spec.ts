import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonRefererBillingDetailComponent } from './person-referer-billing-detail.component';

describe('PersonRefererBilling Management Detail Component', () => {
  let comp: PersonRefererBillingDetailComponent;
  let fixture: ComponentFixture<PersonRefererBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonRefererBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personReferer: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(PersonRefererBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonRefererBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personReferer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personReferer).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
