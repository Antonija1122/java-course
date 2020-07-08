package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollModel;
import hr.fer.zemris.java.p12.model.PollOptionsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author antonija
 *
 */
public class SQLDAO implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollModel> getPollsList() throws DAOException {
		List<PollModel> polls = new ArrayList<PollModel>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollModel unos = new PollModel();
						unos.setId(rs.getLong(1));
						unos.setTitle(rs.getString(2));
						unos.setMessage(rs.getString(3));
						polls.add(unos);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", e);
		}
		return polls;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PollOptionsModel> getPollOptions(long id, String order) throws DAOException {
		List<PollOptionsModel> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from PollOptions where pollID=?  order by " + order);
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOptionsModel unos = new PollOptionsModel();
						unos.setId(rs.getLong(1));
						unos.setOptionTitle(rs.getString(2));
						unos.setOptionLi(rs.getString(3));
						unos.setPollID(rs.getLong(4));
						unos.setVotesCount(rs.getLong(5));
						pollOptions.add(unos);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception e) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", e);
		}
		return pollOptions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PollOptionsModel getPollOption(long id) throws DAOException {
		PollOptionsModel unos = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from PollOptions where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						unos = new PollOptionsModel();
						unos.setId(rs.getLong(1));
						unos.setOptionTitle(rs.getString(2));
						unos.setOptionLi(rs.getString(3));
						unos.setPollID(rs.getLong(4));
						unos.setVotesCount(rs.getLong(5));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return unos;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateVote(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("UPDATE PollOptions set votesCount=votesCount+1 WHERE id=?");
			pst.setLong(1, Long.valueOf(id));
			pst.executeUpdate();
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PollModel getPoll(long id) throws DAOException {
		PollModel unos = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select * from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						unos = new PollModel();
						unos.setId(rs.getLong(1));
						unos.setTitle(rs.getString(2));
						unos.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return unos;
	
	}

}