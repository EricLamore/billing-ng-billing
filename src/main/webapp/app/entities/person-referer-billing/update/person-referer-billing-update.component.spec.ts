jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PersonRefererBillingService } from '../service/person-referer-billing.service';
import { IPersonRefererBilling, PersonRefererBilling } from '../person-referer-billing.model';

import { PersonRefererBillingUpdateComponent } from './person-referer-billing-update.component';

describe('PersonRefererBilling Management Update Component', () => {
  let comp: PersonRefererBillingUpdateComponent;
  let fixture: ComponentFixture<PersonRefererBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personRefererService: PersonRefererBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PersonRefererBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PersonRefererBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonRefererBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personRefererService = TestBed.inject(PersonRefererBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const personReferer: IPersonRefererBilling = { id: 'CBA' };

      activatedRoute.data = of({ personReferer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(personReferer));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonRefererBilling>>();
      const personReferer = { id: 'ABC' };
      jest.spyOn(personRefererService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personReferer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personReferer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(personRefererService.update).toHaveBeenCalledWith(personReferer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonRefererBilling>>();
      const personReferer = new PersonRefererBilling();
      jest.spyOn(personRefererService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personReferer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personReferer }));
      saveSubject.complete();

      // THEN
      expect(personRefererService.create).toHaveBeenCalledWith(personReferer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PersonRefererBilling>>();
      const personReferer = { id: 'ABC' };
      jest.spyOn(personRefererService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personReferer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personRefererService.update).toHaveBeenCalledWith(personReferer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
