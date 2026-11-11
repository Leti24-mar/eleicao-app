import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeCandidatoPage } from './home-candidato.page';

describe('HomeCandidatoPage', () => {
  let component: HomeCandidatoPage;
  let fixture: ComponentFixture<HomeCandidatoPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeCandidatoPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
