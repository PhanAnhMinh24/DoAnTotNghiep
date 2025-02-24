ALTER TABLE friends
ADD COLUMN status ENUM('pending', 'accepted', 'rejected') NOT NULL DEFAULT 'pending';
