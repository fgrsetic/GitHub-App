package com.evolutio.presentation.di

import com.evolutio.presentation.features.calendar.day_fragment.CalendarFragment
import com.evolutio.presentation.features.calendar.day_fragment.DayFragment
import com.evolutio.presentation.features.favorite.FavoritesFragment
import com.evolutio.presentation.features.favorite.competition.FavoriteCompetitionFragment
import com.evolutio.presentation.features.favorite.matches.FavoriteMatchesFragment
import com.evolutio.presentation.features.favorite.teams.FavoriteTeamsFragment
import com.evolutio.presentation.features.league.LeagueCountFragment
import com.evolutio.presentation.features.league.LeagueFragment
import com.evolutio.presentation.features.favorite.competition.competition_matches.CompetitionMatchesFragment
import com.evolutio.presentation.features.match_details.MatchDetailsFragment
import com.evolutio.presentation.features.match_details.new_channel.NewChannelFragment
import com.evolutio.presentation.features.navigation_drawer.DrawerFragment
import com.evolutio.presentation.features.settings.SettingsFragment
import com.evolutio.presentation.features.settings.about_us.AboutUsFragment
import com.evolutio.presentation.features.settings.choose_satellites.SatellitesFragment
import com.evolutio.presentation.features.settings.geo_location_local.CountryPickerFragment
import com.evolutio.presentation.features.settings.primary_sport.PrimarySportFragment
import com.evolutio.presentation.features.splash_screen.SplashScreenFragment
import com.evolutio.presentation.features.team_matches.TeamMatchesFragment
import com.evolutio.presentation.features.today.SortFragment
import com.evolutio.presentation.features.today.TodayFragment
import com.evolutio.presentation.features.today.WeekDaysFragment
import com.evolutio.presentation.shared.DeleteConfirmationFragment
import com.evolutio.presentation.shared.ErrorFragment
import com.evolutio.presentation.shared.MultiPurposeBottomSheetFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    @FragmentScope
    abstract fun contributeSearchFragment(): SearchFragment


    @Scope
    @MustBeDocumented
    annotation class FragmentScope
}